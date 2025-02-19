<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>
	<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<html lang="en">
<head>

 <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=0">
	
	<style type="text/css">
		/* Chart.js */
		@ -webkit-keyframes chartjs-render-animation {
			from {
				opacity: 0.99
			}

			to {
				opacity: 1
			}

		}

		@ keyframes chartjs-render-animation {
			from {
				opacity: 0.99
			}

			to {
				opacity: 1
			}

		}
		
			.page-wrapper {
			background: none !important;
}
		.chartjs-render-monitor {
			-webkit-animation: chartjs-render-animation 0.001s;
			animation: chartjs-render-animation 0.001s;
		}
	</style>
	<style type="text/css">
		.jqstooltip {
			position: absolute;
			left: 0px;
			top: 0px;
			visibility: hidden;
			background: rgb(0, 0, 0) transparent;
			background-color: rgba(0, 0, 0, 0.6);
			filter: progid:DXImageTransform.Microsoft.gradient(startColorstr=#99000000,
					endColorstr=#99000000);
			-ms-filter:
				"progid:DXImageTransform.Microsoft.gradient(startColorstr=#99000000, endColorstr=#99000000)";
			color: white;
			font: 10px arial, san serif;
			text-align: left;
			white-space: nowrap;
			padding: 5px;
			border: 1px solid white;
			z-index: 10000;
		}

		.jqsfield {
			color: white;
			font: 10px arial, san serif;
			text-align: left;
		}

		@media (max-width : 480px) {
			.navbtn {
				display: none;
			}

			.dashbtn {
				display: block !important;
			}
		}

		#right {
			width: 330px;
			background: #fff;
			margin-left: auto;
			margin-right: auto;
		}

		#right .menu {
			 text-align: center;

         font-family: "Helvetica";

         background-color: #005baa;

         color:#fff;

         font-weight:700;

         padding: 12px;

         margin: auto;
		}
		
		
		.paylink {
text-align: center;
    left: 0;
    bottom: 0;
    width: 100%;
    background-color: #fff;
}

  .mt-1{

         margin-top:10px;

         }


.paymode {
    /* margin: 10px; */
    background-color: #ffffff;
    border: 0px;
    /* box-shadow: rgb(0 0 0 / 19%) 0px 10px 20px, rgb(0 0 0 / 23%) 0px 6px 6px; */
    border-radius: 3px;
    color: #34356e;
    font-weight: 700;
    font-size: 17px;
    cursor: pointer;
    /* padding: 5px; */
    height: 100px;
}

		#right .menu1 {
			background: #005baa;
			color: #fff;
		}

		.container-fluid {
			padding: 0px !important;
		}

		.r-bg {
			background-color: #fafafa;
			padding: 12px;
		}

		.home_icon {
			width: 30%;
			margin-left: 10px;
		}

		.column2 {
			color: #005baa;
			font-weight: 700;
			font-family: "Helvetica";
			margin: auto;
		}

		.bg {
			/* display: inline-block; */
			background: #eef3f9;
			border-radius: 5px;
			box-shadow: rgba(50, 50, 93, 0.25) 0px 13px 27px -5px,
				rgba(0, 0, 0, 0.3) 0px 8px 16px -8px;
			/* width:340px; */
			margin: 10px;
			padding: 15px;
			
			margin-bottom: 40px;
margin-top: 30px;
		}

		.bg label {
			font-family: "Helvetica";
			color: #72757c;
		}

		input[type=text] {
			border: 0px !important;
			width: 95% !important;
			outline: 0px !important;
			font-size: 15px !important;
			border-radius: 10px !important;
			padding: 10px !important;
		}

		input[type=email] {
			border: 0px !important;
			width: 95% !important;
			outline: 0px !important;
			font-size: 15px !important;
			border-radius: 10px !important;
			padding: 10px !important;
		}

		input[type=text]:not(.browser-default) {
			background-color: #fff !important;
			border-bottom: 0px !important;
			height: 30px !important;
		}

		input[type=email]:not(.browser-default) {
			background-color: #fff !important;
			border-bottom: 0px !important;
			height: 30px !important;
		}

		input[type=text]:not(.browser-default) {
			background-color: #fff !important;
			border-bottom: 0px !important;
			height: 30px !important;
		}

		[type="checkbox"]:not (:checked),
		[type="checkbox"]:checked {
			position: inherit !important;
			opacity: initial !important;
			pointer-events: auto !important;
		}

		.checkbox-cls {
			color: #0d1115 !important;
			font-weight: 600;
		}

		:focus-visible {
			outline: 0px !important;
		}

		.country {
			border: 0px;
			width: 100%;
			padding: 12px;
			border-radius: 10px;
			margin-top: 6px;
			background-color: #fff;
		}

		.mb-1 {
			margin-bottom: 15px;
		}

		.mb-2 {
			margin-bottom: 25px;
		}

		hr {
			border: none;
			border-top: 1px solid #000;
			color: #fff;
			overflow: visible;
			text-align: center;
			margin-top: 10%;
			margin-bottom: 12%;
		}

		hr:after {
			background: #005bab;
			content: 'or';
			padding: 10px;
			position: relative;
			top: -12px;
			border-radius: 50%;
		}

		.center {
			display: flex;
			/* justify-content: center; */
			align-items: center;
		}

		input:not([type]):focus:not([readonly]),
		input[type=text]:not(.browser-default):focus:not([readonly]),
		input[type=password]:not(.browser-default):focus:not([readonly]),
		input[type=email]:not(.browser-default):focus:not([readonly]),
		input[type=url]:not(.browser-default):focus:not([readonly]),
		input[type=time]:not(.browser-default):focus:not([readonly]),
		input[type=date]:not(.browser-default):focus:not([readonly]),
		input[type=datetime]:not(.browser-default):focus:not([readonly]),
		input[type=datetime-local]:not(.browser-default):focus:not([readonly]),
		input[type=tel]:not(.browser-default):focus:not([readonly]),
		input[type=number]:not(.browser-default):focus:not([readonly]),
		input[type=search]:not(.browser-default):focus:not([readonly]),
		textarea.materialize-textarea:focus:not([readonly]) {
			box-shadow: none !important;
		}

		.center button {
			background: #005bab;
			border: 0px;
			padding: 10px 18px 10px 18px;
			color: #fff;
			box-shadow: rgba(0, 0, 0, 0.16) 0px 1px 4px;
			cursor: pointer;
			border-radius: 7px;
			width: 100%;
		}

		.center .copybtn {
			height: 35px;
			width: 35px;
			border-radius: 50%;
			padding: 0px !important;
			cursor: pointer;
		}

		.row {
			display: flex;
		}

		.usericon {
			width: 10px;
		}

		.column {
			flex: 33.33%;
			padding: 10px;
		}

		select {
			-webkit-appearance: none;
			-moz-appearance: none;
			text-indent: 1px;
			text-overflow: '';
		}

		.shareicon {
			width: 30px;
		}

		/* input:not([type]):focus:not([readonly]), input[type=text]:not(.browser-default):focus:not([readonly]), input[type=password]:not(.browser-default):focus:not([readonly]), input[type=email]:not(.browser-default):focus:not([readonly]), input[type=url]:not(.browser-default):focus:not([readonly]), input[type=time]:not(.browser-default):focus:not([readonly]), input[type=date]:not(.browser-default):focus:not([readonly]), input[type=datetime]:not(.browser-default):focus:not([readonly]), input[type=datetime-local]:not(.browser-default):focus:not([readonly]), input[type=tel]:not(.browser-default):focus:not([readonly]), input[type=number]:not(.browser-default):focus:not([readonly]), input[type=search]:not(.browser-default):focus:not([readonly]), textarea.materialize-textarea:focus:not([readonly]){
            box-shadow: none !important;
         } */
		@media only screen and (max-device-width: 480px) {
			
			.menu {
				font-size: 16px;
				padding: 15px !important;
			}

			#right {
				width: 100%;
			}

			.row .col {
				padding: 0px !important;
			}

			.panel2 label {
				font-size: 1rem;
			}

			input[type=text],
			[type=email] {
				font-size: 1rem;
				padding-bottom: 5px;
				padding-top: 5px;
				height: 30px;
				background-color: white;
			}

			.country {
				font-size: 1rem;
				padding-bottom: 5px;
				padding-top: 5px;
				height: 45px;
			}

			.checkbox-cls {
				font-size: 1rem;
			}

			input.largerCheckbox {
				width: 15px;
				height: 15px;
			}

			hr:after {
				font-size: 12px;
				top: -12px;
			}

			.r-bg {
				padding: 10px;
			}

			.center button {
				font-size: 1rem;
			}

			.column2 {
				font-size: 1.5rem;
			}

			.center .copybtn {
				width: 41px;
				height: 41px;
			}

			.shareicon {
				width: 40px;
			}

			.usericon {
				width: 11px;
			}

			.column {
				padding: 0px;
			}
			
			.paylink { position: fixed;

}
	
	.center #sendbtn {  font-size: 1.5rem;

}
	
			
		}
	</style>
	<style>
		.topbar nav .mailbox.dropdown-content {
			min-width: 240px;
		}

		.u-text {
			margin: auto;
			display: table;
		}

		.c-0e89e3 {
			color: #0e89e3;
		}

		.fs-80-pt {
			font-size: 80%;
		}

		.w15p {
			width: 15%;
		}

		.col-1 {
			width: 8.33%;
			float: left;
			padding: 0 0.75rem
		}

		.col-2 {
			width: 16.66%;
			float: left;
			padding: 0 0.75rem
		}

		.col-3 {
			width: 25%;
			float: left;
			padding: 0 0.75rem
		}

		.col-4 {
			width: 33.33%;
			float: left;
			padding: 0 0.75rem
		}

		.col-5 {
			width: 41.66%;
			float: left;
			padding: 0 0.75rem
		}

		.col-6 {
			width: 50%;
			float: left;
			padding: 0 0.75rem
		}

		.col-7 {
			width: 58.33%;
			float: left;
			padding: 0 0.75rem
		}

		.col-8 {
			width: 66.66%;
			float: left;
			padding: 0 0.75rem
		}

		.col-9 {
			width: 75%;
			float: left;
			padding: 0 0.75rem
		}

		.col-10 {
			width: 83.33%;
			float: left;
			padding: 0 0.75rem
		}

		.col-11 {
			width: 91.66%;
			float: left;
			padding: 0 0.75rem
		}

		.col-12 {
			width: 100%;
			float: left;
			padding: 0 0.75rem
		}

		.hide_key {
			display: none;
		}

		@media only screen and (max-width: 768px) {
			[class*="col-"] {
				width: 100%;
				float: left;
				padding: 0 0.75rem
			}
		}

		.table-new thead tr {
			background: #d7e7ea;
		}

		.table-new thead tr th {
			border-bottom: 1px solid rgba(0, 0, 0, 0.12) !important;
			padding: 10px !important;
		}

		.table-new tbody tr td {
			padding: 10px !important;
		}

		.table-new tbody tr {
			border-bottom: none !important;
		}

		.bg-f3fbfd {
			background-color: #f3fbfd;
		}

		.br-5 {
			border-radius: 5px;
		}

		.c-092540 {
			color: #092540;
		}

		.c-0e89e3 {
			color: #0e89e3;
		}

		.c-151931 {
			color: #151931;
		}

		.mb-40 {
			margin-bottom: 40px;
		}

		.chart_div svg {
			display: block !important;
		}

		.chart_div figure {
			margin: 0px;
		}

		.btccc {
			border-top: solid 1px #ccc;
		}

	
		.align-items-center .c-151931 {
			color: #151931 !important;
			z-index: 2;
		}
		
		 .country-code {

            border-radius: 10px !important;

            padding-left:10px !important;

            width: auto !important;

            background-color: #fff;

            height: 48px !important;

         }

         .country-code:focus {

            color: #212529 !important;

            background-color: #fff !important;

            border-color: #86b7fe !important;

            outline: 0 !important;

            box-shadow: 0 0 0 0.25rem rgb(13 110 253 / 25%) !important;

         }

         .country-code input {

            display: inline-block !important;

            width: auto !important;

            border: none !important;

         }

         .country-code input:focus {

            box-shadow: none !important;

         }
	</style>
</head>

<body>
<form:form id="Motoform" action="${pageContext.request.contextPath}/transactionMoto/motoSubmit" commandName="motoTxnDet" method="post">
   <div class="container-fluid">
      <div class="row">
         <div class="col s12">
            <div class="d-flex align-items-center">
               <div id="right">
<!--                   <div class="menu menu1">EZYMOTO</div> -->
                        <div class="menu">
<!--                            <span class="text1">Amount</span>  -->
                           <span class="text2">
                          RM ${Amount}
                           </span>
                        </div>
                  <div class="r-bg">
                     <div class="row">
<%--                         <img src="${pageContext.request.contextPath}/resourcesNew/img/ezymotocircle.png" --%>
<!--                            class="home_icon"> -->

                     </div>
                     <div class="bg">
                        
                      
                         <div class="dpf-card-placeholder" style="display:none;"></div>
                         
                         <div class="mb-1" id="paymentform">
                                 <label> Card Details</label>
                                 <div class="dpf-input-row m-0 mb-2 mb-sm-3 mt-1">
                                    <div class="dpf-input-container with-icon">
                                       <span class="dpf-input-icon d-none "><i class="fa fa-user" aria-hidden="true"></i></span><!--d-sm-block-->
                                       <input placeholder="Name of Card Holder" autocomplete="off" type="text" name="nameoncard"  id ="cardname" size="4" class="dpf-input form-control ps-2 ps-md-2" data-type="name">
                                    </div>
                                 </div>
                                 <div class="dpf-input-row m-0 mb-2 mb-sm-3">
                                    <div class="dpf-input-container with-icon">
                                       <span class="dpf-input-icon d-none "><i class="fa fa-credit-card" aria-hidden="true"></i></span><!--d-sm-block-->
                                       <input placeholder="Card Number" type="tel" class="dpf-input form-control ps-2 ps-md-2" id="cardnum" name="cardno" size="20" data-type="number">
                                    </div>
                                 </div>
                                 <div class="dpf-input-row row m-0 mb-2 mb-sm-3">
                                    <div class="col-6 col-sm-6 p-0 mb-3" style="width: 54%;">
                                       <div class="dpf-input-container with-icon">
                                          <span class="dpf-input-icon d-none "><i class="fa fa-calendar" aria-hidden="true"></i></span><!--d-sm-block-->
                                          <input placeholder="MM/YY" type="tel"  class="dpf-input form-control ps-2 ps-md-2" id="exp_val" name="expDate" data-type="expiry">
                                       </div>
                                    </div>
                                    <div class="col-sm-5 col-5 offset-1 offset-sm-1 p-0">
                                       <div class="dpf-input-container with-icon">
                                          <span class="dpf-input-icon d-none "><i class="fa fa-lock" aria-hidden="true"></i></span><!--d-sm-block-->
                                          <input placeholder="CVV" type="tel" class="dpf-input form-control ps-2 ps-md-2" id="cvv" name="cvvno" size="4" data-type="cvc">
                                       </div>
                                    </div>
                                 </div>
                              </div>
                              
                              
                              
                              
                                  <input type="hidden" id="referrence" name="referrence" value="${Invoice}" />
      <input type="hidden" id="c_code" name="amount" value="${Amount}" />
                        
                        
                        
<!--                         <div class="center"> -->
<!--                            <button onclick = "TransactionRequest()"> -->
<%--                            <img src="${pageContext.request.contextPath}/resourcesNew/img/send.png" --%>
<!--                               class="usericon" > Send -->
<!--                            </button> -->
<!--                         </div> -->
                        
                        
                         <div class="center">
                           <button  ><!-- onclick = "TransactionRequest()" -->
                            Charge
                           </button>
                        </div>
                        
                        
                     </div>
                     
                  </div>
                  
                   <div class="paylink">

                           <div class="payrow">

                                 <a href="javascript:void(0);"> <img src="${pageContext.request.contextPath}/resourcesNew/img/ezymotocircle.png" class="paymode"></a>

                                 <a href="${pageContext.request.contextPath}/transactionUmweb/Transaction"><img src="${pageContext.request.contextPath}/resourcesNew/img/back.png" class="paymode"  style="padding: 6px;"></a>

                           </div>

                        </div>
                  
                  
               </div>
            </div>
         </div>
      </div>
   </div>
   </form:form>
   
   
   <!--EZYMOTO form  -->
   
<%--    <form:form id="Motoform" action="${pageContext.request.contextPath}/transactionMoto/motoSubmit" commandName="motoTxnDet" method="post"> --%>
<!--       <input type="hidden" id="contactName" name="contactName" value="" /> -->
<%--       <input type="hidden" id="referrence" name="referrence" value="${Invoice}" /> --%>
<%--       <input type="hidden" id="c_code" name="amount" value="${Amount}" /> --%>
<!--       <input type="hidden" id="cardno" name="cardno" value="" /> -->
<!--       <input type="hidden" id="nameoncard" name="nameoncard" value="" /> -->
<!--       <input type="hidden" id="cvvno" name="cvvno" value="" /> -->
<!--       <input type="hidden" id="expDate" name="expDate" value="" /> -->
      
<!--       <input type="hidden" id="multiOption" name="multiOption" value="" /> -->
<!--       <input type="hidden" id="phno" name="phno" value="" /> -->
<!--       <input type="hidden" id="email" name="email" value="" /> -->
<!--       <input type="hidden" id="phncode1" name="phncode1" value="+60"> -->
<%--    </form:form> --%>
   
   
   
</body>



 <script type="text/javascript" src="https://gomobi.io/demo/payment/ezyway/assets/js/DatPayment.js"></script>
      <script type="text/javascript" src="https://gomobi.io/payment/ezyway/assets/js/jquery.validate.min.js"></script>



<script>

/* --------------------------------------------------Country Code Functionalities-------------------------------------------------------  */
function getCountrycode(code) {
   $("#code").html("");
   $("#code").html(code);
   $('#phncode1').val(code);
}
 
 
/* --------------------------------------------------Card Functionalities-------------------------------------------------------  */
 
 
var payment_form = new DatPayment({
    form_selector				: '#paymentform',
    card_container_selector	: '.dpf-card-placeholder',
    number_selector			: '.dpf-input[data-type="number"]',
    date_selector				: '.dpf-input[data-type="expiry"]',
    cvc_selector				: '.dpf-input[data-type="cvc"]',
    name_selector				: '.dpf-input[data-type="name"]',
    placeholders: {
        number		: 'Card Number',
        expiry		: 'MM/YY',
        cvc		: 'CVV',
        name		: 'Name of Card Holder'
    },

    validators: {
       
    }
});


$('#Motoform').validate({ 		
    rules: {
     amount				:{required: true,},
     
    
     cardno				:{required: true,},
     nameoncard			:{required: true,},
     expDate				:{required: true,},
     cvvno					:{required: true,digits: true,},      
    },
  messages: {
	  cardno: "CARD NUMBER REQUIRED",
	  nameoncard: "NAME ON CARD REQUIRED",
	  expDate: "EXPIRY DATE REQUIRED",
	  cvvno:"CVV REQUIRED",
  },
    errorPlacement: function(){
        return false;
    },
    submitHandler: function (form) {          
       //event.preventDefault();
    	TransactionRequest();
    

    }
});
 
 
 
/*--------------------------------------------------Form Validation Functionalities-------------------------------------------------------  */
 
 function validateForm() {

   var CustomerName = document.getElementById("Name").value;
   var referrence = document.getElementById("reference").value;
   var amount = document.getElementById("c_code").value;
   var mobileno = document.getElementById("Reqmobile").value;
   var email = document.getElementById("Reqemail").value;
   if (CustomerName == null || CustomerName == '') {
      alert("Customer Name is Required");
      return false;
   } else if (referrence == null || referrence == '') {
      alert("Reason For Purchase is Required");
      return false;
   } else if (amount == null || amount == '') {
      alert("Amount is Required");
      return false;
   } else if ((email == null || email == '') && (mobileno == null || mobileno == '')) {
      alert("Please enter a contact number or email address.");
      return false;
   } 

else {
      return true;
   }
}
 /*--------------------------------------------------Contact No and Email Validation Functionalities-------------------------------------------------------  */
 
 function validatemailmobno() {
   var email = document.getElementById("Reqemail").value;
   var mobileno = document.getElementById('Reqmobile').value;
   if ((email == null || email == '') && (mobileno == null || mobileno == '')) {
      alert("Please enter a contact number or email address.")
      return false;
   } else {
      if (email !== null && email !== '') {
         var validRegex = /^\w+([\.-]?\w+)*@\w+([\.-]?\w+)*(\.\w{2,3})+$/;
         if (email.match(validRegex)) {
            return true;
         } else {
            alert("incorrect email address!");
            return false;
         }
      } else if (mobileno !== null && mobileno !== '') var validRegexmobile = /^[0-9]+$/; {
         if (mobileno.match(validRegexmobile)) {
            return true;
         } else {
            alert("Enter valid mobile number!");
            return false;
         }
      }
   }
}
 
/*--------------------------------------------------Form Submit Validation Functionalities-------------------------------------------------------  */
  
function TransactionRequest() {
//    const contactName = document.getElementById("contactName")
//    const referrence = document.getElementById("referrence")
//    const multiOption = document.getElementById("multiOption")
//    const phno = document.getElementById("phno")
//    const email = document.getElementById("email")
 //  var checkForm = validateForm();
  // if (checkForm == true) {
     // var checkRequestType = validatemailmobno();
     
     // if (checkRequestType == true) {
//          var CustomerName = document.getElementById("Name").value;
//          var reference = document.getElementById("reference").value;
//          var Reqmobile = document.getElementById("Reqmobile").value;
//          var Reqemail = document.getElementById("Reqemail").value;
//          var checkmultiuse = document.getElementById("multiuse").value;
//          contactName.value = CustomerName;
//          referrence.value = reference;
//          phno.value = Reqmobile;
//          email.value = Reqemail;
//          if (document.getElementById("multiuse").checked) {
//             checkmultiuse = "Yes";
//             multiOption.value = checkmultiuse;
//          } else {
//             checkmultiuse = "No";
//             multiOption.value = checkmultiuse;
//          }
// const cardno = document.getElementById("cardno")
// const nameoncard = document.getElementById("nameoncard")
// const expDate    = document.getElementById("expDate")
// const cvvno    = document.getElementById("cvvno")

// var cardnum = document.getElementById("cardnum").value;
// var cardname = document.getElementById("cardname").value;
// var exp_val = document.getElementById("exp_val").value;
// var cvv = document.getElementById("cvv").value;
// cardno.value = cardnum;
// nameoncard.value = cardname;
// expDate.value = exp_val;
// cvvno.value = cvv;

         var r = confirm("EZYMOTO Transaction Amount is RM " + document.getElementById("c_code").value +
            " \nDo You Want to Submit the Transaction..? ");
         if (r == true) {
            document.getElementById("Motoform").submit();
            return true;
         } else {
            return false;
         }
      //}
  //}
}
	
/*--------------------------------------------------Contact number or Email address to enable and disable functionality.-------------------------------------------------------  */ 
 
const mobileno = document.getElementById("Reqmobile")
const email = document.getElementById("Reqemail")
$("#Reqmobile").focusout(function() {
   var mob = $('#Reqmobile').val();
   if (mob == '') {
      $("#Reqemail").prop("readonly", false);
   } else {
      $("#Reqemail").prop("readonly", true);
   }
});
$("#Reqemail").focusout(function() {
   var em = $('#Reqemail').val();
   if (em == '') {
      $("#Reqmobile").prop("readonly", false);
   } else {
      $("#Reqmobile").val('');
      $("#Reqmobile").prop("readonly", true);
   }
})
</script>

</html>