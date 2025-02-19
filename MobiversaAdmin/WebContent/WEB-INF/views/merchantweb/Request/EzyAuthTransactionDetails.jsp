<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>
	<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<html lang="en">
<head>

<!-- Include SweetAlert2 CSS -->
<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/sweetalert2@11/dist/sweetalert2.min.css">

<!-- Include SweetAlert2 JS -->
<script src="https://cdn.jsdelivr.net/npm/sweetalert2@11"></script>


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
			color: #005baa;
			font-weight: 700;
			padding: 12px;
			margin: auto;
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
			padding: 8px !important;
		}

		input[type=email] {
			border: 0px !important;
			width: 95% !important;
			outline: 0px !important;
			font-size: 15px !important;
			border-radius: 10px !important;
			padding: 8px !important;
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
   <div class="container-fluid">
      <div class="row">
         <div class="col s12">
            <div class="d-flex align-items-center">
               <div id="right">
                  <div class="menu menu1">EZYAUTH</div>
                  <div class="r-bg">
                     <div class="row">
                        <img src="${pageContext.request.contextPath}/resourcesNew/img/ezyauthcircle.png"
                           class="home_icon">
                        <div class="column2">
                           <span class="text1">Amount</span><br> <span class="text2">
                           RM ${Amount}
                           </span>
                        </div>
                     </div>
                     <div class="bg">
                        <div class="mb-1">
                           <label><img src="${pageContext.request.contextPath}/resourcesNew/img/user_copy.png"
                              class="usericon"> Name</label> <input type="text"  value="" id="Name" 
                              width="100">
                        </div>
                        <div class="mb-1">
                           <label><img src="${pageContext.request.contextPath}/resourcesNew/img/price_tag.png"
                              class="usericon"> Reason for Purchase</label> <input type="text" value=""
                              width="100" id="reference">
                        </div>
<%--                        
                        <div class="mb-1">
                           <label><img src="${pageContext.request.contextPath}/resourcesNew/img/flag.png"
                              class="usericon"> Country</label> 
                         <select class="country" onchange="getCountrycode(this.value)">
                          <option value="+60">Malaysia</option>
                          <c:forEach items="${listCountry}" var="lc">
                          <option value="${lc.phoneCode}">${lc.countryName}</option>
                          </c:forEach>
                          </select>
                        </div>
                        <div class="mb-2">
                           <label><img src="${pageContext.request.contextPath}/resourcesNew/img/phone.png" class="usericon"> Contact</label>
                           <div class="country-code mt-2 d-inline-block">
                              <span class="border-end  px-2" id="code">+60</span>
                              <input type="text" value="" id="Reqmobile" placeholder="">
                           </div>
                        </div>
                        <hr>
                        <div class="mb-2">
                           <input type="email" value="" width="100" placeholder="Email" id="Reqemail">
                        </div>
                         
                        <div class="center">
                           <button onclick = "TransactionRequest()">
                           <img src="${pageContext.request.contextPath}/resourcesNew/img/send.png"
                              class="usericon" > Send
                           </button>
                        </div>
--%>

						<div class="mb-0">
                           <label><img src="${pageContext.request.contextPath}/resourcesNew1/assets/mail_box1.svg" class="usericon"> Email</label>
                        </div>
                        
						<div class="mb-2">
                           <input type="email" value="" width="100" placeholder="Email" id="Reqemail">
                        </div>

						<div class="center button-container">
						<button onclick="TransactionRequest()" style="margin-right: 12px;">
							<img
								src="${pageContext.request.contextPath}/resourcesNew/img/send.png"
								class="usericon" style="margin-right: 6px;">Send
						</button>
						<button onclick="TransactionRequestForCopy()">
							<img
								src="${pageContext.request.contextPath}/resourcesNew/img/icons8-copy-50.png"
								class="usericon" style="margin-right: 6px;">Copy
						</button>
						</div>								
                        
                        <div style="text-align: end; margin-top: 5px;">
                           <a href="${pageContext.request.contextPath}/transactionUmweb/Transaction">Back</a>
                        </div>
                     </div>
                  </div>
               </div>
            </div>
         </div>
      </div>
   </div>
   <!--EZYAUTH form  -->
   <form:form id="Authform" action="${pageContext.request.contextPath}/merchantpreauth/preAuthSubmit" commandName="preAuthTxnDet" method="post">
      <input type="hidden" id="contactName" name="contactName" value="" />
      <input type="hidden" id="referrence" name="referrence" value="" />
      <input type="hidden" id="c_code" name="amount" value="${Amount}" />
      <input type="hidden" id="phno" name="phno" value="" />
      <input type="hidden" id="email" name="email" value="" />
      <input type="hidden" id="phncode1" name="phncode1" value="+60">
   </form:form>
</body>
<script>

/* --------------------------------------------------Country Code Functionalities-------------------------------------------------------  */
function getCountrycode(code) {
   $("#code").html("");
   $("#code").html(code);
   $('#phncode1').val(code);
}
 
/*--------------------------------------------------Form Validation Functionalities-------------------------------------------------------  */
 
 function validateForm() {
   var CustomerName = document.getElementById("Name").value;
   var referrence = document.getElementById("reference").value;
   var amount = document.getElementById("c_code").value;
   var email = document.getElementById("Reqemail").value;
   var emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
   
   if (CustomerName == null || CustomerName == '') {
      alert("Customer Name is Required");
      return false;
   } else if (referrence == null || referrence == '') {
      alert("Reason For Purchase is Required");
      return false;
   } else if (amount == null || amount == '') {
      alert("Amount is Required");
      return false;
   } else if (email == null || email == '') {
      alert("Please enter an email address.");
      return false;
   } else if (!emailRegex.test(email)) {
       alert("Please enter a valid email address.");
       return false;
   } else {
      return true;
   }
}

/*--------------------------------------------------Form Validation Functionalities-------------------------------------------------------  */
 
 function validateFormForCopy() {
   var CustomerName = document.getElementById("Name").value;
   var referrence = document.getElementById("reference").value;
   var amount = document.getElementById("c_code").value;
   
   if (CustomerName == null || CustomerName == '') {
      alert("Customer Name is Required");
      return false;
   } else if (referrence == null || referrence == '') {
      alert("Reason For Purchase is Required");
      return false;
   } else if (amount == null || amount == '') {
      alert("Amount is Required");
      return false;
   } else {
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

/* function TransactionRequest() {
    const contactName = document.getElementById("contactName");
    const referrence = document.getElementById("referrence");
    const email = document.getElementById("email");

    var checkForm = validateForm();
    if (checkForm == true) {
        const CustomerName = document.getElementById("Name").value;
        const reference = document.getElementById("reference").value;
        const Reqemail = document.getElementById("Reqemail").value;
        contactName.value = CustomerName;
        referrence.value = reference;
        email.value = Reqemail;
        
        var r = confirm("The EZYAUTH transaction amount is RM " + document.getElementById("c_code").value +
        ".\nDo You Want to Submit the Transaction..?");
        if (r == true) {
            $("#overlay").show();
           document.getElementById("Authform").submit();
           return true;
        } else {
           return false;
        }

        const transactionAmount = document.getElementById("c_code").value;

        // SweetAlert2 confirmation dialog
        Swal.fire({
            title: "Confirm Transaction",
            text: `The EZYAUTH transaction amount is RM ${transactionAmount}. Do you want to submit this transaction?`,
            icon: "question",
            showCancelButton: true,
            confirmButtonText: "Yes, Submit",
            cancelButtonText: "No, Cancel",
            focusCancel: true
        }).then((result) => {
            if (result.isConfirmed) {
                $("#overlay").show();
                document.getElementById("Authform").submit();
            }
        });
    }
} 
*/

/*--------------------------------------------------Form Submit Validation Functionalities FOR 'COPY'-------------------------------------------------------  */
function TransactionRequestForCopy() {        
    const contactName = document.getElementById("contactName");
    const referrence = document.getElementById("referrence");
    const checkForm = validateFormForCopy();
    var amount = document.getElementById("c_code").value;

    if (checkForm === true) {
        const CustomerName = document.getElementById("Name").value;
        const reference = document.getElementById("reference").value;

        contactName.value = CustomerName;
        referrence.value = reference;
        
/* 	 var r = confirm("EZYAUTH Transaction Amount is RM " + document.getElementById("c_code").value +
        " \nDo you want to submit this transaction without sending an email..?");
     if (r == true) {
         $("#overlay").show();
        document.getElementById("Authform").submit();
        return true;
     } else {
        return false;
     } */

//        const transactionAmount = document.getElementById("c_code").value;
     
		var formatterAmount = amount ? parseFloat(amount) : 0;
		formatterAmount = formatterAmount.toLocaleString();

        Swal.fire({
            title: "Confirm Transaction",
            text: "The EZYAUTH transaction amount is "+ formatterAmount +" RM. Would you like to proceed without sending an email?",
            icon: "question",
            iconColor: "#005BAA",// Blue icon color
            showCancelButton: true,
            confirmButtonText: "Yes, Submit",
            cancelButtonText: "No, Cancel",
            focusCancel: true,
            confirmButtonColor: "#005BAA", // Blue confirm button
            cancelButtonColor: "#6c757d"  // Optional: Gray cancel button
        }).then((result) => {
            if (result.isConfirmed) {
                $("#overlay").show();
                document.getElementById("Authform").submit();
            }
        });
    }
}

/*--------------------------------------------------Form Submit Validation Functionalities-------------------------------------------------------  */

function TransactionRequest() {
   const contactName = document.getElementById("contactName");
   const referrence = document.getElementById("referrence");
   const email = document.getElementById("email");
   var checkForm = validateForm();
   var amount = document.getElementById("c_code").value;

   if (checkForm == true) {

         var CustomerName = document.getElementById("Name").value;
         var reference = document.getElementById("reference").value;
         var Reqemail = document.getElementById("Reqemail").value;
         contactName.value = CustomerName;
         referrence.value = reference;
         email.value = Reqemail;
         
/* 		 var r = confirm("EZYAUTH Transaction Amount is RM " + document.getElementById("c_code").value +
         " \nDo You Want to Submit the Transaction..? ");
	      if (r == true) {
	         document.getElementById("Authform").submit();
	         return true;
	      } else {
	         return false;
	      } 
*/

//         const transactionAmount = document.getElementById("c_code").value;
		var formatterAmount = amount ? parseFloat(amount) : 0;
		formatterAmount = formatterAmount.toLocaleString();

         // SweetAlert2 confirmation dialog
         Swal.fire({
             title: "Confirm Transaction",
             text: "The EZYAUTH transaction amount is "+ formatterAmount +" RM. Do you want to submit this transaction?",
             icon: "question",
             iconColor: "#005BAA",// Blue icon color
             showCancelButton: true,
             confirmButtonText: "Yes, Submit",
             cancelButtonText: "No, Cancel",
             focusCancel: true,
             confirmButtonColor: "#005BAA", // Blue confirm button
             cancelButtonColor: "#6c757d"  // Optional: Gray cancel button
         }).then((result) => {
             if (result.isConfirmed) {
                 $("#overlay").show();
                 document.getElementById("Authform").submit();
             }
         });
      }
   
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