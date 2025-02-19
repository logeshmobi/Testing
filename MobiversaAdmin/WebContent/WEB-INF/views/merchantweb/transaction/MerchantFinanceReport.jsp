<%@page import="com.mobiversa.payment.controller.PayoutUserController"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>
<%@ page language="java" import="java.util.*"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page import="java.util.ResourceBundle"%>
<%
	ResourceBundle resource = ResourceBundle.getBundle("config");
	String actionimg = resource.getString("NEWACTION");
	String voidimg = resource.getString("NEWVOID");
	String refundimg = resource.getString("NEWREFUND");
	String eyeimg = resource.getString("NEWEYE");
%>

<html lang="en-US">
<head>
<meta charset="UTF-8">
<meta
	content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no"
	name="viewport">



<meta http-equiv="Content-Security-Policy"
	content="default-src 
    'self'">

<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath}/resourcesNew1/dist/css/pages/custom.css">
	
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/3.1.1/jquery.min.js"></script>
<script
	src="https://ajax.googleapis.com/ajax/libs/jqueryui/1.12.1/jquery-ui.min.js"></script>
<script
	src="https://cdnjs.cloudflare.com/ajax/libs/html2canvas/1.4.1/html2canvas.min.js"></script>
 
<script
	src="https://cdnjs.cloudflare.com/ajax/libs/html2canvas/1.4.1/html2canvas.min.js"></script>
<script
	src="https://cdnjs.cloudflare.com/ajax/libs/FileSaver.js/2.0.5/FileSaver.min.js"></script>
<script
	src="https://cdnjs.cloudflare.com/ajax/libs/dom-to-image/2.6.0/dom-to-image.min.js"></script>

<!-- Script tag for Datepicker 	 -->
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/3.1.1/jquery.min.js"></script>
<script
	src="https://ajax.googleapis.com/ajax/libs/jqueryui/1.12.1/jquery-ui.min.js"></script>

</head>



<style>
#merchantName:hover {
	color: 275ca8;
}

#agentName:hover {
	color: 275ca8;
}

.example_e1:focus {
	outline: none !important;
}

.table-border-bottom tr {
	border-bottom: 1px solid rgba(0, 0, 0, 0.12);
}

.key_hover:hover {
	cursor: pointer;
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
	font-style: Arial, Helvetica, sans-serif;
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

.hide_key {
	display: none;
}
</style>


<script type="text/javascript">

	var size ="${paginationBean.querySize}";
	console.log("total size of pagination from backend is : "+ size)


	history.pushState(null, null, "");
	window.addEventListener('popstate', function() {
		history.pushState(null, null, "");

	});
</script>

<style>
.mobi-modal {
	position: fixed;
	top: 25%;
	left: 0;
	z-index: 1060;
	width: 100%;
	height: 100%;
	overflow-x: hidden;
	overflow-y: auto;
	outline: 0;
}

.mobi-modal-dialog {
	max-width: 500px;
	margin: 1.75rem auto;
}

.mobi-modal-content {
	position: relative;
	display: flex;
	flex-direction: column;
	width: 100%;
	pointer-events: auto;
	background-color: #fff;
	background-clip: padding-box;
	border: 1px solid rgba(0, 0, 0, .2);
	border-radius: 14px;
	outline: 0;
}

.mobi-popup-small {
	font-size: 13px;
	color: #808080;
}

.mobi-modal-header {
	display: flex;
	flex-shrink: 0;
	align-items: center;
	justify-content: space-between;
	padding: 1rem 1rem;
	/*	border-bottom: 1px solid #dee2e6; */
	border-top-left-radius: calc(0.3rem - 1px);
	border-top-right-radius: calc(0.3rem - 1px);
	font-size: 22px !important;
}

.mobi-modal-body {
	position: relative;
	flex: 1 1 auto;
	padding: 1rem;
	font-size: 20px !important;
}

.fz-22 {
	font-size: 22px
}

.mobi-modal-footer {
	display: flex;
	flex-wrap: wrap;
	flex-shrink: 0;
	align-items: center;
	justify-content: flex-end;
	padding: 0.75rem;
	/*	border-top: 1px solid #dee2e6; */
	border-bottom-right-radius: calc(0.3rem - 1px);
	border-bottom-left-radius: calc(0.3rem - 1px);
}

.mobi-modal-title {
	margin-bottom: 0;
	line-height: 1.5;
}

.mobi-popup-btn-secondary {
	color: #fff !important;
	background-color: #A1A0A2 !important;
	border-color: #A1A0A2 !important;
}

.mobi-text-dark {
	padding: 14px;
	color: #212529;
}

.mobi-text-dark1 {
	color: #212529;
}

.c969696 {
	color: #080808;;
	font-size: 1.2rem !important;
}

.mobi-popup-btn {
	display: inline-block;
	font-weight: 400;
	line-height: 1.5;
	color: #212529;
	text-align: center;
	text-decoration: none;
	vertical-align: middle;
	cursor: pointer;
	-webkit-user-select: none;
	-moz-user-select: none;
	user-select: none;
	background-color: transparent;
	border: 1px solid transparent;
	padding: 0.375rem 0.75rem;
	font-size: 1rem;
	border-radius: 0.25rem;
	transition: color .15s ease-in-out, background-color .15s ease-in-out,
		border-color .15s ease-in-out, box-shadow .15s ease-in-out;
}

#mobi_modal_popup {
	display: none;
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

#overlay-popup1 {
	position: fixed;
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

.mobi-text-right {
	text-align: right;
}

.date_circle {
	width: 80px;
	height: 80px;
	border-radius: 40px;
	color: #fff;
	font-weight: 600;
	display: flex;
	align-items: center !important;
	justify-content: center !important;
	font-size: 20px;
}

.gradient864cf5 {
	background: rgb(134, 76, 245);
	background: linear-gradient(156deg, rgba(134, 76, 245, 1) 39%,
		rgba(161, 115, 250, 1) 75%, rgba(159, 119, 238, 1) 100%);
}

.gradientf551c1 {
	background: rgb(245, 81, 193);
	background: linear-gradient(156deg, rgba(245, 81, 193, 1) 39%,
		rgba(251, 135, 215, 1) 75%, rgba(255, 161, 237, 1) 100%);
}

.gradient5b45f8 {
	background: rgb(91, 69, 248);
	background: linear-gradient(156deg, rgba(91, 69, 248, 1) 39%,
		rgba(119, 100, 252, 1) 75%, rgba(119, 100, 252, 1) 100%);
}

.gradient11ac4d {
	background: rgb(17, 172, 77);
	background: linear-gradient(156deg, rgba(17, 172, 77, 1) 39%,
		rgba(55, 200, 106, 1) 75%, rgba(67, 215, 116, 1) 100%);
}

.gradientfc496e {
	background: rgb(252, 73, 110);
	background: linear-gradient(156deg, rgba(252, 73, 110, 1) 39%,
		rgba(254, 73, 111, 1) 75%, rgba(254, 122, 149, 1) 100%);
}

.mobi-mr-10 {
	/*	margin-right: 10px; */
	margin-right: 43px;
}
</style>

<style>
.export_div .select-wrapper {
	width: 65%;
	float: left;
}

.datepicker {
	width: 80% !important;
}

.select-wrapper .caret {
	fill: #005baa;
}

.mobi-text-right {
	text-align: right;
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
td, th {
	padding: 7px 8px;
	color: #707070;
}

.table-border-bottom td {
	border-bottom: 1px solid rgba(0, 0, 0, 0.12) !important;
}

thead th {
	border-bottom: 1px solid #ffa500;
	color: #4377a2;
}

img.refund_icon_6 {
	width: 40px;
	height: 40px;
	margin: 10px 9px 0 10px;
	object-fit: contain;
}

.w24 {
	width: 24px;
}

#decl {
	left: 40%;
	bottom: 40%;
	display: none;
	z-index: 199;
}

.decline-side-box {
	display: block;
	width: 25rem;
	height: 13rem;
	background-color: #FFFFFF;
	position: fixed;
	border-radius: 10px;
	border: 1px solid #FFDEAD;
}

.text-wrap-user {
	position: absolute;
	width: 22rem;
	height: 11rem;
	background-color: #FFFFFF;
	left: 20px;
	top: 10px;
	border-radius: 10px;
}

.text-style-decl {
	font-size: 1.6rem;
	font-weight: 500;
	font-family: sans-serif;
	color: #4377a2;
	padding: 15px 25px;
	margin: 5px 0;
}

#decline-reason {
	padding: 15px 0 0 0;
	font-size: 1.2rem;
	margin: 0 30px;
	width: 75%;
}

.bn632-hover {
	width: 80px;
	font-size: 16px;
	font-weight: 600;
	color: #fff;
	cursor: pointer;
	margin: 25px;
	height: 40px;
	text-align: center;
	border: none;
	background-size: 300% 100%;
	border-radius: 50px;
	moz-transition: all .4s ease-in-out;
	-o-transition: all .4s ease-in-out;
	-webkit-transition: all .4s ease-in-out;
	transition: all .4s ease-in-out;
	position: absolute;
	left: 35px;
	bottom: -20px;
}

.bn632-hover:hover {
	background-position: 100% 0;
	moz-transition: all .4s ease-in-out;
	-o-transition: all .4s ease-in-out;
	-webkit-transition: all .4s ease-in-out;
	transition: all .4s ease-in-out;
}

.bn632-hover:focus {
	outline: none;
}

.bn632-hover.bn26 {
	background-image: linear-gradient(to right, #25aae1, #4481eb, #04befe, #3f86ed);
	box-shadow: 0 4px 15px 0 rgba(65, 132, 234, 0.75);
}

.cancelbtn {
	width: 80px;
	font-size: 16px;
	font-weight: 600;
	color: #fff;
	cursor: pointer;
	margin: 20px;
	height: 40px;
	text-align: center;
	border: none;
	background-size: 300% 100%;
	border-radius: 50px;
	moz-transition: all .4s ease-in-out;
	-o-transition: all .4s ease-in-out;
	-webkit-transition: all .4s ease-in-out;
	transition: all .4s ease-in-out;
	position: absolute;
	left: 160px;
	bottom: -15px;
}

.cancelbtn:hover {
	background-position: 100% 0;
	moz-transition: all .4s ease-in-out;
	-o-transition: all .4s ease-in-out;
	-webkit-transition: all .4s ease-in-out;
	transition: all .4s ease-in-out;
}

.cancelbtn:focus {
	outline: none;
}

.cancelbtn.bn25 {
	/*   background-image: linear-gradient(
    to right,
    #29323c,
    #485563,
    #2b5876,
    #4e4376
  ); */
	background-color: #7D7D7D;
	/* box-shadow: 0 4px 15px 0 rgba(45, 54, 65, 0.75); */
}

#exampleModalCenter {
	z-index: 99;
	width: 25%;
	font-size: 24px;
	font-weight: 400;
	font-family: 'Poppins', sans-serif;
	text-align: center;
}

#label_businessname {
	position: static !important;
	color: #858585;
}

.businessname .select2-container {
	margin: 7px 0 !important;
	border: 1.7px solid #005baa !important;
}

#label_businessname {
	position: static !important;
	color: #858585;
}

input[type=search]:not(.browser-default) {
	border-bottom: 1.5px solid orange !important;
}

#label_businessname {
	position: static !important;
	color: #858585;
}

.businessname .select2-container {
	margin: 7px 0 !important;
	border: 1.7px solid #005baa !important;
}


/* DEMO STARTS HERE  */
.container-fluid{
    padding: 20px 18px !important;
    font-family: "Poppins", sans-serif !important;
}

.select2-dropdown{
    font-family: "Poppins", sans-serif !important;
    z-index:1;
}

.select2-container--default .select2-search--dropdown .select2-search__field {
    font-family: "Poppins", sans-serif !important;
}

.heading_text {
	font-family: "Poppins", sans-serif !important;
	font-weight: 600 !important;
	font-size: 20px !important;
}

.mb-0 {
	margin-bottom: 0 !important;
}

.select2-container--default .select2-selection--single {
	border: none !important;
}

#label_businessname {
	position: static !important;
	color: #858585;
}

.businessname .select2-container {
	margin: 7px 0 !important;
	border: 1.7px solid #005baa !important;
}

.row .select2-container {
	padding: 4px 10px !important;
	font-size: 14px !important;
	z-index: 1 !important;
}

.select2-container--default .select2-selection--single .select2-selection__arrow
	{
	top: 4px;
	right: 10px;
}

.align-right {
	text-align: right !important;
}

.align-center {
	text-align: center;
}

.heading_row {
	border-bottom: 1.5px solid orange !important;
}

#data_list_table tr td {
	font-weight: 400;
	font-size: 14px;
	color: #929292;
	padding: 12px;
}

#data_list_table tr th {
	font-weight: 600;
	font-size: 14px;
	padding: 12px;
	white-space: nowrap;
}

.complete_status {
	color: #41b441;
	font-weight: 500;
	margin-left: 5px;
}

.failed_status {
	color: red;
	font-weight: 500;
	margin-left: 5px;
}

.btn_more {
	border: none;
	background-color: transparent;
	cursor: pointer;
	display: flex;
	align-items: center;
	justify-content: center;
	box-shadow: none !important;
}

.btn_more:hover, .btn_more:focus {
	background-color: transparent;
	!
	important;
}

#more_details {
	/*cursor: pointer;*/
	display: flex;
	align-items: center;
	justify-content: center;
}

/*    modal */
.modal_container {
	display: none;
	position: fixed;
	z-index: 100;
	left: 0;
	top: 0;
	width: 100%;
	height: 100%;
	overflow: auto;
	background-color: rgb(0, 0, 0);
	background-color: rgba(0, 0, 0, 0.4);
	scrollbar-width: none;
}

.more_details_modal_class {
	/* min-width: 400px !important;
             width: fit-content !important;
             max-width: 35% !important;*/
	background-color: #fff !important;
	border-radius: 10px !important;
	width: 80%;
	margin: 0 auto;
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
}

.refno_info, .type_info {
	display: flex;
	margin-bottom: 10px !important;
}

.left_content {
	flex: 0.22;
	color: #005baa;
	white-space: nowrap;
	font-weight: 500;
}

.colon {
	flex: 0.05;
	color: #005baa;
	white-space: nowrap;
	font-weight: 500;
}

.right_content {
	flex: 1;
	color: #586570;
}

input[type=search]:not(.browser-default) {
	border-bottom: 1.5px solid orange !important;
}

input[type=search]:not(.browser-default):focus:not([readonly]) {
	box-shadow: none !important;
}

.reason_info {
	color: #005baa;
	margin-bottom: 10px !important;
	font-weight: 500;
}

.withdraw_reason_text::placeholder {
	color: #d0d0d0;
	font-size: 13px;
	padding: 5px;
}

.withdraw_reason_text {
	width: 100%;
	height: 6rem;
	padding: 5px;
	background-color: transparent;
	resize: none;
	border-radius: 5px;
	border: 1px solid #70707070;
	scrollbar-width: thin;
	color: #586570;
	font-family: "Poppins", sans-serif;
}

.closebtn {
	background-color: #005baa;
	border-radius: 50px;
	height: 33px !important;
	line-height: 33px !important;
	padding: 0 30px;
	font-size: 12px;
}

.closebtn:hover, .closebtn:focus {
	background-color: #005baa !important;
}

#pagination {
	display: inline-block;
	vertical-align: middle;
	border-radius: 1px;
	padding: 1px 2px 4px 2px;
	border-top: 1px solid transparent;
	border-bottom: 1px solid transparent;
	background-color: transparent;
	float: right;
	margin-right: 15px;
	margin-bottom: 10px;
	font-family: 'Poppins', sans-serif;
}

#pagination a, #pagination i {
	display: inline-block;
	vertical-align: middle;
	width: 2.2rem;
	text-align: center;
	font-size: 16px;
	padding: 2.5px;
	-webkit-user-select: none;
	-moz-user-select: none;
	-ms-user-select: none;
	-o-user-select: none;
	user-select: none;
}

#pagination a {
	margin: 0 2px;
	border-radius: 1px;
	border: 1px solid #005baa;
	cursor: pointer;
	background-color: white;
	color: #005baa;
	height: 2.3rem;
	vertical-align: middle;
	padding-top: 4px;
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

  #label_businessname {
    position: static !important;
    color: #858585;
}

.modal-content {
    padding: 10px 30px;
    font-family: "Poppins", sans-serif;
}

.refno_info, .type_info {
    display: flex;
    margin-bottom: 10px !important;
}

.left_content {
    flex: 0.22;
    color: #005baa;
    white-space: nowrap;
    font-weight: 500;
}

.colon {
    flex: 0.05;
    color: #005baa;
    white-space: nowrap;
    font-weight: 500;
}

.right_content {
    flex: 1;
    color: #586570;
}

input[type=search]:not(.browser-default) {
    border-bottom: 1.5px solid orange !important;
}

input[type=search]:not(.browser-default):focus:not([readonly]) {
    box-shadow: none !important;
}

.reason_info {
    color: #005baa;
    margin-bottom: 10px !important;
    font-weight: 500;
}

.withdraw_reason_text::placeholder {
    color: #d0d0d0;
    font-size: 13px;
    padding: 5px;
}

.withdraw_reason_text {
    width: 100%;
    height: 6rem;
    padding: 5px;
    background-color: transparent;
    resize: none;
    border-radius: 5px;
    border: 1px solid #70707070;
    scrollbar-width: thin;
    color: #586570;
    font-family: "Poppins", sans-serif;
}

.businessname .select-filter {
    position: relative;
    z-index: 100;
}

.businessname .select-filter::after {
    content: '';
    position: absolute;
    bottom: 100%;
    left: 0;
    right: 0;
    z-index: 90;
    border: 1px solid #ccc;
    background: #fff;
}

.businessname select:focus + .select-filter::after {
    z-index: 200;
}

</style>


<script lang="JavaScript">



	function loadSelectData() {
    		$("#overlay").show();
    		//alert("test"+document.getElementById("txnType").value);
    		var e = document.getElementById("from").value;
    		var e1 = document.getElementById("to").value;

    		var fromDate = new Date(e);//.toDateString("yyyy-MM-dd");
    		var toDate = new Date(e1);//.toDateString("yyyy-MM-dd");

    		var fromday = fromDate.getDate();
    		var frommon = fromDate.getMonth() + 1;
    		var fromyear = fromDate.getFullYear();

    		var today = toDate.getDate();
    		var tomon = toDate.getMonth() + 1;
    		var toyear = toDate.getFullYear();

    		var fromdateString = fromyear + '-'
    				+ (frommon <= 9 ? '0' + frommon : frommon) + '-'
    				+ (fromday <= 9 ? '0' + fromday : fromday);
    		var todateString = toyear + '-' + (tomon <= 9 ? '0' + tomon : tomon)
    				+ '-' + (today <= 9 ? '0' + today : today);

    		if (e == null || e == '' || e1 == null || e1 == '') {
    			alert("Please Select date(s)");
    			$("#overlay").hide();
    		}
    		else {
    			document.getElementById("dateval1").value = fromdateString;
    			document.getElementById("dateval2").value = todateString;
    			/* document.getElementById("txnType").value = e2; */
    			<%--/* document.location.href = '${pageContext.request.contextPath}/transaction/searchUMEzyway?date='--%>
    			// 		+ e + '&date1=' + e1 + '&txnType=' + e2; */

    			document.location.href = '${pageContext.request.contextPath}/payoutDataUser/searchPayout?date='
    					+ fromdateString + '&date1=' + todateString;


    			localStorage.setItem("fromDate", e);
    			localStorage.setItem("toDate", e1);
    			form.submit;

    		}
    	}




window.addEventListener('load', function () {
		var fromDate = localStorage.getItem("fromDate");
		var toDate = localStorage.getItem("toDate");

		if (fromDate && toDate) {

			document.getElementById("from").value = fromDate;
			document.getElementById("to").value = toDate;


			document.getElementById("datef").style.transform = "translateY(-14px) scale(0.8)";
			document.getElementById("datet").style.transform = "translateY(-14px) scale(0.8)";
		}

		localStorage.removeItem("fromDate");
		localStorage.removeItem("toDate");
		//localStorage.clear();
	});




function loadSelectData2() {
		var e = document.getElementById("from").value;
		var e1 = document.getElementById("to").value;
		var fromDate = new Date(e);
		var toDate = new Date(e1);
		var PageNumber = document.getElementById("pgnum").value;
		//alert("Page Number: " + PageNumber);

		var fromday = fromDate.getDate();
		var frommon = fromDate.getMonth() + 1;
		var fromyear = fromDate.getFullYear();

		var today = toDate.getDate();
		var tomon = toDate.getMonth() + 1;
		var toyear = toDate.getFullYear();

		var fromdateString = fromyear + '-'
				+ (frommon <= 9 ? '0' + frommon : frommon) + '-'
				+ (fromday <= 9 ? '0' + fromday : fromday);
		var todateString = toyear + '-' + (tomon <= 9 ? '0' + tomon : tomon)
				+ '-' + (today <= 9 ? '0' + today : today);

		if (e === '' || e1 === '') { // Check for empty strings instead of null
			fromdateString = null;
			todateString = null;
			//alert("from: " + fromdateString + " to: " + fromdateString);
			document.location.href = '${pageContext.request.contextPath}/payoutDataUser/searchPayout?date='
					+ fromdateString + '&date1=' + todateString + '&currPage=' + PageNumber;
		} else {

			document.getElementById("dateval1").value = fromdateString;
			document.getElementById("dateval2").value = todateString;

			// alert("from: " + fromdateString + " to: " + todateString);
			document.location.href = '${pageContext.request.contextPath}/payoutDataUser/searchPayout?date='
					+ fromdateString + '&date1=' + todateString + '&currPage=' + PageNumber;

			console.log(fromdateString);
			console.log(todateString);
			console.log(PageNumber);

			/* Updated code... */
			localStorage.setItem("fromDate", e);
			localStorage.setItem("toDate", e1);

			form.submit(); // Added parentheses to actually call the function
		}
	}

function loadExpData() {
    var fromDate = document.getElementById("from").value;
    var toDate = document.getElementById("to").value;
    var exportType = document.getElementById("export1").value;
   
    var fromDateObj = new Date(fromDate);
    var toDateObj = new Date(toDate);

    var timeDiff = Math.abs(toDateObj.getTime() - fromDateObj.getTime());
    var diffDays = Math.ceil(timeDiff / (1000 * 3600 * 24));
    
    var fromDateAfterFormated = new Date(fromDate);
	var toDateAfterFormated = new Date(toDate);

	var fromday = fromDateAfterFormated.getDate();
	var frommon = fromDateAfterFormated.getMonth() + 1;
	var fromyear = fromDateAfterFormated.getFullYear();

	var today = toDateAfterFormated.getDate();
	var tomon = toDateAfterFormated.getMonth() + 1;
	var toyear = toDateAfterFormated.getFullYear();

    var fromDateString = (fromday <= 9 ? '0' + fromday : fromday) + '/' + (frommon <= 9 ? '0' + frommon : frommon) + '/' + fromyear;
	var toDateString = (today <= 9 ? '0' + today : today) + '/' + (tomon <= 9 ? '0' + tomon : tomon) + '/' + toyear;

    if (!fromDate || !toDate) {
        alert("Please Select date(s)");
    } else if (!exportType) {
        alert("Please Select Export Type");
    }else if (diffDays > 30) {
        alert("Please select a date range of 30 days or less.");
    } else {
    	document.location.href = '${pageContext.request.contextPath}/transactionUmweb/MerchantexportPayinTxnAsEmail?date='+ fromDateString + '&date1=' + toDateString + '&export=' + exportType  + '&username=' + '${username}';
    }
}



	  
	function loadDropDate13() {

		var e = document.getElementById("export");

		var strUser = e.options[e.selectedIndex].value;
		document.getElementById("export1").value = strUser;

	}

		function loadDate(inputtxt, outputtxt) {
    		var field = inputtxt.value.trim();
    		var regex = /^(?:Jan|Feb|Mar|Apr|May|Jun|Jul|Aug|Sep|Oct|Nov|Dec)\s\d{2},\s\d{4}$/;
    		if (!regex.test(field)) {
    			alert("Please enter a valid date format");
    			inputtxt.value = '';
    			outputtxt.value = '';
    			return;
    		}
    		outputtxt.value = field;
    	}



	function payoutIdSearch() {
    		$("#overlay").show();
    		var Value = document.getElementById('drop_option').value.trim();
    		var searchInput = document.getElementById('searchApi').value.trim();

    		var regex = /^[a-zA-Z0-9_\s]*$/;
    		if (searchInput === '' && Value === 'choose-type') {
    			alert('Please fill all the Data');
    			$("#overlay").hide();
    			return;
    		} else if (searchInput === '') {
    			alert("Search value must not be empty");
    			$("#overlay").hide();
    			return;
    		} else if (!regex.test(searchInput)) {
    			alert("Search value must not contain special symbols");
    			$("#overlay").hide();
    			return;
    		} else if (Value === '' || Value === 'choose-type') {
    			alert("Choose option must be selected");
    			$("#overlay").hide();
    			return;
    		}
    		if (searchInput.trim() === '' || Value.trim() === '') {
    			alert('Please enter a search value.');
    			$("#overlay").hide();
    			return;
    		}
    		document.location.href = '${pageContext.request.contextPath}/searchNew/searchPayoutAndTxnIdPayoutLogin?VALUE=' + searchInput + '&transaction_type='
    				+ Value;

    		form.submit;

    	}
	
    $(document).ready(function () {
        $(".select-filter").select2();
    });





	
	
</script>
<body class="">
	<div class="container-fluid">
		<div class="row">
			<div class="col s12">
				<div class="card blue-bg text-white">
					<div class="card-content">
						<div class="d-flex align-items-center">
							<h3 class="text-white">
								<strong>Transaction Report</strong>
							</h3>
						</div>


					</div>
				</div>
			</div>
		</div>



<%-- 		<div class="row">
   <div class="col s12">
      <div class="card blue-bg text-white">
         <div class="card-content" style="padding:0 24px">
            <div class="row">
               merchant choose
               <div class="col s12 m4 l4 input-field businessname">
                  <label for="merchantName" id="label_businessname">Business Name</label> 
                  <select name="merchantName" id="businessName" path="merchantName" class="browser-default select-filter">
                     <optgroup label="Business Names" style="width: 100%">
                        <option selected value="">
                           <c:out value="Business Name" />
                        </option>
                        <c:forEach var="merchant" items="${businessNamesAndUsernames}">
                           <c:if test="${merchant[0]!=''}">
                              <option value="${merchant[1]}">${merchant[0]}</option>
                           </c:if>
                        </c:forEach>
                     </optgroup>
                  </select>
               </div>
            </div>
         </div>
      </div>
   </div>
</div> --%>

<!-- DEMO --> 



		<!-- no record found popup -->
		<div class="modal fade bd-example-modal-xl pop-body"
			style="width: 500px !important; height: 270px !important;"
			id="exampleModalCenter" tabindex="-1" role="dialog"
			aria-labelledby="mySmallModalLabel" aria-hidden="true"
			style="text-align:center;">
			<div class="modal-dialog modal-xl">
				<div class="modal-content "
					style="padding: 0 !important; margin: 0 !important;">
					<p class="pop-head"
						style="background-color: #f9f9f9; width: 100%; height: 60px; color: #005baa; padding-top: 12px; border-bottom: 2px solid #ffa500;">Information</p>
					<img
						src="${pageContext.request.contextPath}/resourcesNew1/assets/NoRecordPNG.png"
						width="60px !important; height:60px !important;">
					<p id="innerText" style="font-size: 22px;"></p>
					<div class="modal-footer">
						<button type="button" class="btn btn-secondary"
							data-dismiss="modal" id="close" onclick="closepopup()"
							style="width: 106px !important; height: 38px !important; font-size: 18px; border-radius: 50px !important; margin-right: 187px !important; letter-spacing: 0.8px; font-family: 'Poppins', sans-serif; font-weight: medium; transform: translateY(-10px);">Close</button>
					</div>
				</div>
			</div>
		</div>

		<div class="row">
			<div class="col s12">
				<div class="card border-radius">
					<div class="card-content padding-card">
						<div class="row">
							<!-- From Date -->
							<div class="input-field col s12 m3 l3">
								<label for="from" style="margin: 0px;">From </label> <input
									type="text" id="from" name="fromDate"
									class="validate datepicker"
									onchange="loadDate(document.getElementById('from'),document.getElementById('date11'))">
								<i class="material-icons prefix">date_range</i>
							</div>
							<!-- To Date -->
							<div class="input-field col s12 m3 l3">
								<label for="to" style="margin: 0px;">To</label> <input
									type="text" id="to" name="toDate" class="datepicker"
									onchange="loadDate(document.getElementById('to'),document.getElementById('date12'))">
								<i class="material-icons prefix">date_range</i>
							</div>
							<!-- Business Name -->

							<!-- Export Type -->
							<div class="input-field col s12 m3 l3">
								<select id="export1" name="export">
									<option value="" selected>Choose</option>
									<option value="EXCEL">EXCEL</option>
								</select> <label for="export1">Export Type</label>
							</div>
							<!-- Export Button -->
							<div class="input-field col s12 m3 l3">
								<div class="button-class">
									<button
										class="export-btn waves-effect waves-light btn btn-round indigo three-btn-one"
										type="button" onclick="return loadExpData();">Export</button>
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>



		<!-- mobi loading logo -->
		<div id="overlay" id="loading-gif">
			<div id="overlay_text">
				<img class="img-fluid"
					src="${pageContext.request.contextPath}/resourcesNew1/assets/loader.gif">
			</div>
		</div>
        <!-- modal to get email -->

	<%-- 	<div id="email-modal-id" class="email-modal-class"
			style="display: none;">
			<div class="email-content-class">
				<div
					style="text-align: center; border-bottom: 1.5px solid #ffa500; padding: 2%;">
					<p style="font-size: 16px; font-weight: 500; color: #005baa;">Notification</p>
				</div>
				<div
					style="display: flex; flex-direction: column; align-items: center; gap: 8px;">
					<div
						style="display: flex; flex-direction: column; align-items: center; gap: 5px; padding: 5px 0; width: 80%;">
						<img
							src="${pageContext.request.contextPath}/resourcesNew1/assets/mail_box1.svg"
							width="45px">
						<div style="width: 100%; text-align: left;">
							<p style="color: #586570;">Please enter your email to receive
								the Excel files.</p>
						</div>
					</div>
					<div style="padding: 0 10%; width: 100%;">
						<input type="email" id="email-input" name="email"
							placeholder="username@gomobi.io"
							style="width: 100%; border-bottom: 1.5px solid #ffa500;">
						<p id="emailErrorMessage"
							style="color: red; font-size: 12px; text-align: center; display: none;">Email
							must end with @gomobi.io</p>
					</div>
				</div>
				<div
					style="display: flex; flex-direction: row; gap: 10px; width: 100%; align-items: center; padding: 2%; justify-content: center; background-color: #b1dcfb30; border-bottom-right-radius: 9px; border-bottom-left-radius: 9px;">
					<button type="button" id="email-submit-btn-cancel"
						class="email-submit-class" onclick="closeNotify()"
						style="background: transparent; border: 2px solid #005baa; color: #005baa;">Cancel</button>
					<button type="button" id="email-submit-btn-submit"
						class="email-submit-class" onclick="submitEmail()">Submit</button>
				</div>
			</div>
		</div> --%>


		<style>
.custom-table {
	width: 100%;
	margin-top: 10px;
	font-size: 15px;
	border-collapse: collapse;
}

.custom-table tr {
	display: flex;
	border-bottom-color: #fff;
}

.custom-table  .summary_heading {
	font-size: 18px;
	color: #005baa;
}

.custom-table .popup-key {
	flex: 0.6 !important;
}

.custom-table .hyphen {
	flex: 0.3 !important;
}

.custom-table td {
	border-bottom: 1px solid white;
	padding: 8px;
	flex: 1;
}

.custom-table th {
	
}
</style>

		<script>






	document.addEventListener('DOMContentLoaded', function () {
    			const closeModalBtn = document.getElementById('X-slip');
    			closeModalBtn.addEventListener('click', function () {
    				const modal = document.getElementById('slip-modal-id');
    				modal.style.display = 'none';
    			});
    		});
	
	   document.getElementById("email-close-btn").onclick = function() {
		    document.getElementById("email-modal-id").style.display = "none";
		    document.getElementById("emailErrorMessage").style.display = "none";
		    document.getElementById("email-input").value = "";
		}
	   
	/*    function submitEmail() {
		   $("#overlay").show();
		    var email = document.getElementById("email-input").value.trim();
		    var fromDate = document.getElementById("from").value;
		    var toDate = document.getElementById("to").value;
		    var exportType = document.getElementById("export1").value;
		    var emailErrorMessage = document.getElementById("emailErrorMessage");
		    var businessName = document.getElementById("businessName").value;

		    
		    
			var fromDateAfterFormated = new Date(fromDate);
			var toDateAfterFormated = new Date(toDate);

			var fromday = fromDateAfterFormated.getDate();
			var frommon = fromDateAfterFormated.getMonth() + 1;
			var fromyear = fromDateAfterFormated.getFullYear();

			var today = toDateAfterFormated.getDate();
			var tomon = toDateAfterFormated.getMonth() + 1;
			var toyear = toDateAfterFormated.getFullYear();

		    var fromDateString = (fromday <= 9 ? '0' + fromday : fromday) + '/' + (frommon <= 9 ? '0' + frommon : frommon) + '/' + fromyear;
			var toDateString = (today <= 9 ? '0' + today : today) + '/' + (tomon <= 9 ? '0' + tomon : tomon) + '/' + toyear;

		    
		    debugger;
		    if (email === "") {
		        alert("Please enter an email address");
		        return;
		    }

		    if (!email.endsWith("@gomobi.io")) {
		        emailErrorMessage.style.display = "block";
		        return false;
		    } else {
		        emailErrorMessage.style.display = "none";
		    }
	    
		    
		    
		   /*  document.location.href = "${pageContext.request.contextPath}/transaction/exportPayinTxnAsEmail?date=${fromDate}&date1=${toDate}&emailId=${email}&export=${exportType}&username=${businessName}";
		     */
			document.location.href = '${pageContext.request.contextPath}/transaction/exportPayinTxnAsEmail?date='+ fromDateString + '&date1=' + toDateString + '&emailId=' + email+ '&export=' + exportType + '&username=' + businessName;
		    
		} */

	 
	   
	   
		$(document).ready(function() {
			// $('#data_list_table').DataTable();
		});

		$(document).ready(
				function() {
					$('#data_list_table').DataTable({
						"bSort" : false,
						"stateSave" : true,
						"stateSaveParams" : function(settings, data) {
							data.length = settings._iDisplayLength;
						},
					});

					var savedLength = parseInt($('#data_list_table')
							.DataTable().state().length, 10);
					if (savedLength) {
						$('#data_list_table').DataTable().page.len(savedLength)
								.draw('page');
					}
				});

		


		function closeNotify(){
			document.getElementById("email-modal-id").style.display = "none";
			document.getElementById("emailErrorMessage").style.display = "none";
			document.getElementById('email-input').value = '';
		}


		function closeDialog() {
			$("#mobi_modal_popup").hide();
			$("#overlay-popup").hide();
		}



/* 3 MONTHS ONLY SHOW IN DATE PICKER */
        
        /* jQuery(function() {		
        	var date = new Date();
        	var currentMonth = date.getMonth();
        	var currentDate = date.getDate();
        	var currentYear = date.getFullYear();
        	
        	$('.datepicker').datepicker({
        		minDate: new Date(currentYear, currentMonth-2, currentDate),
        		maxDate: new Date(currentYear, currentMonth, currentDate+1)
        	});
        });
        
        $('.pickadate-clear-buttons').pickadate({
          close: 'Close Picker', 
        formatSubmit: 'dd/mm/yyyy',
        }); */
        
        /* $('.datepicker').pickadate(); */
	</script>

		</script>


		<style>
.email-modal-class {
	display: none;
	position: fixed;
	z-index: 1;
	left: 0;
	top: 0;
	width: 100%;
	height: 100%;
	overflow: auto;
	background-color: rgba(0, 0, 0, 0.4);
}

.email-content-class {
	background-color: #fefefe;
	margin: 15% auto;
	border: 1px solid #888;
	max-width: 33%;
	height: auto;
	border-radius: 11px;
}

.email-close-class {
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

.email-submit-class {
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

.email-submit-class:focus, .email-submit-class:active {
	background-color: #005baa;
}
}
</style>

		<script>


		function formatDate(dateString) {
			console.log("Input dateString:", dateString);
			var parts = dateString.split(" ");
			var datePart = parts[0].split("/");
			var timePart = parts[1];
			var formattedDateString = datePart[2] + "-" + datePart[1] + "-" + datePart[0] + " " + timePart; // Added space before time
			var date = new Date(formattedDateString);
			console.log("Date object:", date);

			var monthNames = ["January", "February", "March", "April", "May", "June",
				"July", "August", "September", "October", "November", "December"];

			var day = date.getDate();
			var monthIndex = date.getMonth();
			console.log("Month index:", monthIndex);
			var year = date.getFullYear();
			var hours = date.getHours();
			var minutes = date.getMinutes();
			var seconds = date.getSeconds();
			hours = hours < 10 ? '0' + hours : hours;
			minutes = minutes < 10 ? '0' + minutes : minutes;
			seconds = seconds < 10 ? '0' + seconds : seconds;
			// var formattedDate = day+"-"+ monthNames[monthIndex].substring(0, 3) + "-" + year + ", " + " " + hours + ":" + minutes + ":" + seconds;
			var formattedDate = (day < 10 ? '0' + day : day) + "-" + monthNames[monthIndex].substring(0, 3) + "-" + year + ", " + " " + hours + ":" + minutes + ":" + seconds;
			console.log("Formatted date:", formattedDate);
			return formattedDate;
		}




	</script>


		<div id="pagination"></div>
		<input type="hidden" id="pgnum">


		<script>




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
				//console.log(Pagination.size);
				<%--// Pagination.size = Math.ceil(${paginationBean.fullCount}/10) ||100;--%>

				Pagination.size = Math.ceil(${paginationBean.querySize} / 20);
				<%--Pagination.size = ((${paginationBean.currPage})+4) ||100;--%>
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


			// last function
			// Last: function() {
			// 	Pagination.code += '<a onclick="dynamic(((Pagination.page)+1))">'+ ((Pagination.page)+1)+ '</a>'+'<a onclick="dynamic(((Pagination.page)+2))">'+ ((Pagination.page)+2)+ '</a>'+'<a onclick="dynamic(((Pagination.page)+3))">'+ ((Pagination.page)+3)+ '</a>'+'<i>...</i>';
			// },


			Last: function() {

				// total page
				var lastPage = Math.ceil(${paginationBean.querySize} / 20);
				// three pg no after 1st pg no
				if (lastPage > Pagination.page + 3) {
					// generate <a> tag for 3 pg no
					for (var i = Pagination.page + 1; i <= Pagination.page + 3; i++) {
						Pagination.code += '<a onclick="dynamic(' + i + ')">' + i + '</a>';
					}
					Pagination.code += '<i>...</i>';
//         Pagination.code += '<a onclick="dynamic(' + lastPage + ')">' + lastPage + '</a>';
				} else {
					// if less than 3 page generate <a> tag
					for (var i = Pagination.page + 1; i <= lastPage; i++) {
						Pagination.code += '<a onclick="dynamic(' + i + ')">' + i + '</a>';
					}
				}
			},



			// old code
			// add first page with separator
			// First: function() {
			// 	if(Pagination.page==1){
			//
			// 		Pagination.code += '<i>...</i>'+'<a onclick="dynamic(Pagination.page)" id="page1">'+Pagination.page+'</a>';
			//
			// 	}
			// 	else{
			// 		Pagination.code += '<a>1</a>'+'<i>...</i>'+'<a  onclick="dynamic(((Pagination.page)-1))">'+((Pagination.page)-1)+'</a>'+'<a id="page2" onclick="dynamic(Pagination.page)">'+Pagination.page+'</a>';
			// 	}
			// },


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
					'<a onclick="previous(${paginationBean.currPage})" style="width:7rem;font-weight:bold;font-size:14px;height:2.3rem;padding-top:6px; " id="previous">&#60;&#60; Previous</a>', // previous button
					'<span></span>',  // pagination container
					'<a onclick="next(${paginationBean.currPage})" style="width:6.5rem;font-weight:bold;font-size:14px;height:2.3rem;padding-top:6px;" id="nxt">Next &#62;&#62;</a>'  // next button
				];

				e.innerHTML = html.join('');
				Pagination.e = e.getElementsByTagName('span')[0];
				Pagination.Buttons(e);

			

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
					var paginationContainer = document.getElementById("pagination");
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



		<style>
.slip-table tr td {
	padding: 4px 8px;
}

.modal1 {
	display: none;
	position: fixed;
	z-index: 9999;
	left: 0;
	top: 0;
	width: 100%;
	height: 100%;
	overflow: hidden;
	background-color: rgba(0, 0, 0, 0.5);
}

.modal-content1 {
	background-color: #fefefe;
	margin: 5% auto;
	padding: 20px;
	border: 1px solid #888;
	width: 80%;
	max-width: 38%;
	border-radius: 11px;
	box-shadow: 0 4px 8px rgba(0, 0, 0, 0.2);
	/*overflow-y: hidden;*/
	max-height: calc(106% - 172px);
}

.close1 {
	color: #aaa;
	float: right;
	font-size: 28px;
	font-weight: bold;
}

.close:hover, .close:focus {
	color: black;
	text-decoration: none;
	cursor: pointer;
}

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

.close-Declined {
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

.close-Declined:focus, .close-Declined:active {
	background-color: #005baa; /* Same color as default */
}

.reason-class {
	color: black;
	font-size: 17px;
	display: flex;
	justify-content: center;
	align-items: center;
}

.declined-img {
	display: flex;
	justify-content: center;
	align-items: center;
}

.declin-img-img {
	height: 51px;
	position: relative;
	bottom: 6px;
}

.payoutDetailsClass {
	display: none;
	position: fixed;
	z-index: 999;
	left: 0;
	top: 0;
	width: 100%;
	height: 100%;
	background-color: rgba(0, 0, 0, 0.4);
}

/*.payoutDetailsClass-content {*/
/*	background-color: #fefefe;*/
/*	margin: 15% auto;*/
/*	border: 0px solid #888;*/
/*	max-width: 60%;*/
/*	border-radius: 7px;*/
/*	height: auto;*/
/*}*/

/*  for within scroll */
.payoutDetailsClass-content {
	background-color: #fefefe;
	margin: 2% auto;
	border: 0px solid #888;
	max-width: 60%;
	border-radius: 7px;
	height: auto;
	overflow-y: auto !important;
	height: 95%;
	scrollbar-width: none;
}

.payoutDetailsClass::-webkit-scrollbar {
	width: 0;
}

.yellow-line-morePayout {
	background-color: #f0c207;
	height: 1.9px;
	position: absolute;
	top: 260px;
	width: calc(84% - 359px);
	left: 21.5%;
}

.payout-head {
	font-size: 18px;
	color: #005baa;
	display: flex;
	justify-content: center;
	align-items: center;
}

.transaction-details {
	text-align: left;
	font-size: 20px;
	margin-top: 20px;
	font-weight: bold;
	color: #005baa;
}

.close-payoutDetails {
	font-size: 34px;
	position: absolute;
	bottom: 392px;
	left: 1036px;
	cursor: pointer;
	cursor: pointer;
}

.payoutDetail-btn-class {
	background-color: #005baa;
	color: white;
	border-radius: 25px;
	border: none;
	padding: 10px 27px;
	font-size: 12px;
	height: 35px;
	outline: none;
	cursor: pointer
}

.payoutDetail-btn-class:focus, .payoutDetail-btn-class:active {
	background-color: #005baa; /* Same color as default */
}

.payoutDetail-btn-class-div {
	display: flex;
	justify-content: center;
	margin-top: 24px;
}

.popup-value {
	display: flex;
}

.slip-modal-class {
	display: none;
	position: fixed;
	z-index: 1000;
	left: 0;
	top: 0%;
	width: 100%;
	height: 100%;
	overflow: auto;
	scrollbar-width: none;
	background-color: rgba(0, 0, 0, 0.4);
}

.slip-modal-content-class {
	background-color: #fefefe;
	margin: 15% auto;
	/*	padding:  10px;*/
	border: 1px solid #888;
	width: 92%;
	max-width: 460px;
	border-radius: 15px;
	height: auto;
}

.slip-modal-close-btn-class {
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

.slip-modal-close-btn-class:focus, .slip-modal-close-btn-class:active {
	background-color: #005baa;
}

.slipModal-close-btn {
	display: flex;
	align-items: center;
	justify-content: center;
}

.payment-slip-text {
	color: #005baa;
	font-size: 18px;
}

.slip-yellow-line {
	background-color: #f0c207;
	height: 1.9px;
	position: absolute;
	top: 260px;
	width: calc(66% - 450px);
	left: 33.5%;
}

#pagination {
	display: inline-block;
	vertical-align: middle;
	border-radius: 1px;
	padding: 1px 2px 4px 2px;
	border-top: 1px solid transparent;
	border-bottom: 1px solid transparent;
	background-color: transparent;
	float: right;
	margin-right: 15px;
	margin-bottom: 10px;
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
	width: 2.2rem;
	/*  color: #7D7D7D; */
	text-align: center;
	font-size: 16px;
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
	border-radius: 1px;
	border: 1px solid #005baa;
	cursor: pointer;
	/* box-shadow: inset 0 1px 0 0 #D7D7D7, 0 1px 2px #666; */
	/* text-shadow: 0 1px 1px #FFF; */
	background-color: white;
	color: #005baa;
	height: 2.3rem;
	vertical-align: middle;
	padding-top: 4px;
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

.editBtn {
	text-decoration: none;
	color: #039be5;
	-webkit-tap-highlight-color: transparent !important;
	border: none;
	cursor: pointer;
	background: none;
}

.three-btn-one {
	    background-color: #005baa !important;
    color: #fff !important;
    border-radius: 20px !important;

	border-width: 2px;
	font-weight: 530;
	height: 42px !important;
	line-height: 40px !important;
	padding: 0 30px !important;
	margin-right:80px;
}
.three-btn-one:hover {
  background-color: transparent;
  color: #005baa;	 
}

.three-btn-search {
	background-color: white !important;
	color: #005baa !important;
	border-style: solid;
	border-color: #005baa !important;
	border-width: 2px;
	font-weight: 530;
	height: 42px !important;
	line-height: 40px !important;
	padding: 0 30px !important;
	margin-left: 14px ! important;
}

/* empty modal pop up */
#exampleModalCenter {
	z-index: 99;
	width: 25%;
	font-size: 24px;
	font-weight: 400;
	font-family: 'Poppins', sans-serif;
	text-align: center;
}

.three-btn-search {
	background-color: white !important;
	color: #005baa !important;
	border-style: solid;
	border-color: #005baa !important;
	border-width: 2px;
	font-weight: 530;
	height: 42px !important;
	line-height: 40px !important;
	padding: 0 30px !important;
	margin-left: 14px ! important;
}
</style>



		<input type="hidden" id="txnType"> <input type="hidden"
			id="pag">


		<script>

		var txnType = document.getElementById("txnType").value="${paginationBean.TXNtype}";
		if (txnType){
			document.getElementById("pagination").style.display="none";
		}

		if(${paginationBean.itemList.size()}==0){
			document.getElementById("no-data").innerText = "No data available";
			var hello = document.getElementById("no-data").value;
			console.log("size is : "+hello);

		}

		function closepopup(){
			document.getElementById("exampleModalCenter").style.display ="none";
			document.getElementById("pop-bg-color").style.display ="none";

		}

		/* 3 MONTHS ONLY SHOW IN DATE PICKER */

		jQuery(function() {
			var date = new Date();
			var currentMonth = date.getMonth();
			var currentDate = date.getDate();
			var currentYear = date.getFullYear();

			$('.datepicker').datepicker({
				minDate: new Date(currentYear, currentMonth-2, currentDate),
				maxDate: new Date(currentYear, currentMonth, currentDate+1)
			});
		});

		$('.pickadate-clear-buttons').pickadate({
			close: 'Close Picker',
			formatSubmit: 'dd/mm/yyyy',
		});

		/* $('.datepicker').pickadate(); */

	</script>
	
	<input type="hidden" id="isEmailTriggered">


		<div id="successMailId" class="confirmation_email_class">
			<div class="confirmation_email_content_class">
				<div>
					<div
						style="padding: 15px 24px; text-align: center; font-size: 16px; color: #005baa; font-weight: 500; border-bottom: 1.5px solid #ffa500;">
						Notification</div>
					<div class="confirmation_icon_div"
						style="display: flex; justify-content: center;">
						<img class="confirmation_icon_img1" height="40px">
					</div>

					<div style="padding: 15px 30px; text-align: center;">

						<p id="emailTrueOrFalse" style="color: #586570"></p>
					</div>
					<div
						style="padding: 10px; display: flex; justify-content: center; background-color: #005baa25;">
						<button type="button" class="successMail_btn"
							onclick="closeSuccessEmail()">Close</button>
					</div>
				</div>

			</div>

		</div>

<script>
function closeSuccessEmail() {
    var modal = document.getElementById("successMailId");
    if (modal) {
        modal.style.display = "none";
    }
}
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

		
		<script type="text/javascript">
		
   
        var message = "${isEmailSent}";
        if (message && message.trim().length > 0 && message !== "null") {
            var isEmailTriggered = document.getElementById("isEmailTriggered").value = "${isEmailSent}";

            console.log("email :::: " + isEmailTriggered);

            if (isEmailTriggered === "true") {
                var modal = document.getElementById("successMailId");
                if (modal) {
                    modal.style.display = "block";

                    var ptag = document.getElementById("emailTrueOrFalse");
                    if (ptag) {
                        ptag.innerHTML = "Please check your inbox for the file";
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


        }else{
            console.log("not found")
        }

    

    
  
    
    
</script>
		
		
<script>
    var body = document.body;
    var initialOverflow = body.style.overflow;
    
    console.log("hi",${isEmailSent});
</script>

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
</style>

  <div id="overlay">
    <div id="overlay_text">
        <img class="img-fluid"
             src="${pageContext.request.contextPath}/resourcesNew1/assets/loader.gif">
    </div>
</div>

<script>

jQuery(function() {		
	var date = new Date();
	var currentMonth = date.getMonth();
	var currentDate = date.getDate();
	var currentYear = date.getFullYear();
	
	$('.datepicker').datepicker({
		minDate: new Date(currentYear, currentMonth-2, currentDate),
		maxDate: new Date(currentYear, currentMonth, currentDate+1)
	});
});

$('.pickadate-clear-buttons').pickadate({
  close: 'Close Picker', 
formatSubmit: 'dd/mm/yyyy',
});
</script>

</body>

</html>