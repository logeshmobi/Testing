<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">

<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>addsubmerchant</title>

<link rel="preconnect" href="https://fonts.googleapis.com">
<link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
<link
	href="https://fonts.googleapis.com/css2?family=Poppins:ital,wght@0,100;0,200;0,300;0,400;0,500;0,600;0,700;0,800;0,900;1,100;1,200;1,300;1,400;1,500;1,600;1,700;1,800;1,900&display=swap"
	rel="stylesheet">

<link rel="stylesheet"
	href="${pageContext.request.contextPath}/resourcesNew/css/niceCountryInput.css">
<script
	src="${pageContext.request.contextPath}/resourcesNew/js/niceCountryInput.js"></script>

<script src="https://code.jquery.com/jquery-1.12.4.min.js"></script>


<style>
@import
	url('https://fonts.googleapis.com/css2?family=Poppins:ital,wght@0,100;0,200;0,300;0,400;0,500;0,600;0,700;0,800;0,900;1,100;1,200;1,300;1,400;1,500;1,600;1,700;1,800;1,900&display=swap')
	;

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

@media only screen and (min-width: 993px) {
	.row .col.l5 {
		width: 45% !important;
	}
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
}

#country_label {
	transform: translateY(-30px) scale(0.8) !important;
	transform-origin: 0 0;
}

.input-field>label:not(.label-icon).active {
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

input:not([type]):focus:not([readonly]), input[type="text"]:not(.browser-default):focus:not([readonly])
	{
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
}

.cancelbtn {
	background-color: #fff;
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
	background-color: #fff !important;
	color: #005baa !important;
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
}

.content_updatepopup {
	display: flex;
	flex-direction: column;
	align-items: center;
	justify-content: center;
	padding: 7px 30px !important;
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
								<strong class="heading_text">Add Sub Merchant</strong>

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
							<form id="businessdetailform"
								action="${pageContext.request.contextPath}/submerchant/add-submerchant/Request"
								method="post">
								<input type="hidden" name="${_csrf.parameterName}"
									value="${_csrf.token}" />
								<div class="row">
									<div class="input-field col s12 m5 l5">
										<input type="text" id="businessName" name="businessName"
											placeholder="AZ EMPIRE BEAUTY & HEALTH CARE"
											oninput="validateBusinessName()" /> <label
											for="businessName">Business Name</label> <span id="error_msg"
											style="font-size: 10px; color: red; display: none;">
											Name already in use. Please try a different one </span>
									</div>

									<div class="input-field col s12 m5 offset-m1 offset-l1 l5">
										<input type="text" id="email" name="email"
											placeholder="info.beauteegorgeous@gmail.com" /> <label
											for="email">Email</label>
									</div>

								</div>

								<div class="row">
									<div class="input-field  col s12 m5 l5">
										<input type="text" id="website" name="website"
											placeholder="www.infobeauti.my" /> <label for="website">Website</label>
									</div>
									<div class="input-field col s12 m5 offset-m1 offset-l1 l5">
										<input type="text" id="industry" name="industry"
											placeholder="Cosmetic & Beauty" /> <label for="industry">Industry</label>
									</div>

								</div>

								<div class="row" style="margin-top: 25px;">
									<div class="input-field  col s12 m5 l5">
										<label for="country" id="country_label">Country</label> <input
											type="hidden" id="country_hidden" name="country"> <input
											type="hidden" id="country_name_hidden" name="country_name">

										<div id="country" name="country"
											class="niceCountryInputSelector" data-selectedcountry="MY"
											data-showcontinentsonly="false" data-showflags="true"
											data-i18nall="All selected" data-i18nnofilter="No selection"
											data-i18nfilter="Filter"
											data-onchangecallback="onChangeCallback"
											data-showspecial="true"></div>


									</div>
								</div>


								<div class="row btn_row">
									<button type="button" id="btnbtn" class="btn blue-btn"
										onclick="openConfirmPopup()">Submit</button>

								</div>
							</form>
						</div>

					</div>
				</div>
			</div>


		</div>


		<div class="confirm_overlay" id="confirm_overlay">
			<div class="row modal_row">
				<div class="col offset-l4 offset-m3 s12 m6 l4">
					<div id="confirm-modal" class="confirm_modal">
						<div class="modal-header">
							<p class="mb-0">Notification</p>
						</div>
						<div class="modal-content ">
							<div class="align-center">
								<img
									src="${pageContext.request.contextPath}/resourcesNew1/assets/submerchantregister/addsubmerchant.svg"
									width="50" height="50">
							</div>
							<p class="align-center popup_messages">Would you like to
								create this as a sub-merchant account?</p>
						</div>
						<div class="align-center modal-footer footer">
							<button id="close_btn" class="btn cancelbtn" type="button"
								onclick="closePopupModal()" name="action">Cancel</button>
							<button id="create_btn" class="btn confirmbtn " type="button"
								onclick="submitDetails()" name="action">Create</button>

						</div>
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
							<button id="closebtn_result" class="btn blue-btn closebtn"
								type="button" onclick="closeresultModal()" name="action">Close</button>

						</div>
					</div>
				</div>
			</div>
		</div>

		<input type="hidden" id="update_result"
			value="${registrationResponse}" />

	</div>

	<script>
	
	
	
	/* function validateBusinessName() {
		var inputBusinessName = document.getElementById("businessName").value.trim().toUpperCase();
		console.log(inputBusinessName);
		var dbLoadedBusinessnames = ${LoadedBusinessNames};
		var errorMsg = document.getElementById("error_msg");
		var button = document.getElementById("btnbtn");
 
		// If input is empty or only whitespace
		if (inputBusinessName === "") {
			errorMsg.style.display = "none";
			button.disabled = false;
			button.style.opacity = "1";
		} else {
			if (dbLoadedBusinessnames.includes(inputBusinessName)) {
				errorMsg.style.display = "block";
				button.disabled = true;
				button.style.opacity = "0.5";
			} else {
				errorMsg.style.display = "none";
				button.disabled = false;
				button.style.opacity = "1";
			}
		}
	} */
	
	
	function validateBusinessName() {
	    var inputBusinessName = document.getElementById("businessName").value.trim().toUpperCase();
	    console.log(inputBusinessName);

	    var dbLoadedBusinessnames = ${LoadedBusinessNames}; // Ensure this is set to a valid JS array
	    var errorMsg = document.getElementById("error_msg");
	    var button = document.getElementById("btnbtn");

	    // Regular expression to check if the input contains only numbers
	    var isOnlyNumbers = /^[0-9]+$/.test(inputBusinessName);

	    // If input is empty or only whitespace
	    if (inputBusinessName === "") {
	        errorMsg.style.display = "none";
	        button.disabled = false;
	        button.style.opacity = "1";
	    } else if (isOnlyNumbers) {
	        // If input contains only numbers
	        errorMsg.innerText = "Business name cannot contain only numbers.";
	        errorMsg.style.display = "block";
	        button.disabled = true;
	        button.style.opacity = "0.5";
	    } else if (dbLoadedBusinessnames.includes(inputBusinessName)) {
	        // If business name is already in the database
	        errorMsg.innerText = "Business name already exists.";
	        errorMsg.style.display = "block";
	        button.disabled = true;
	        button.style.opacity = "0.5";
	    } else {
	        // Input is valid
	        errorMsg.style.display = "none";
	        button.disabled = false;
	        button.style.opacity = "1";
	    }
	}

	
	
	

   // document.getElementById("result_overlay").style.display = "block";

    function closePopupModal() {
        document.getElementById("confirm_overlay").style.display = "none";
    }

    function closeresultModal(){
        document.getElementById("result_overlay").style.display = "none";

    }



    //
    // function openConfirmPopup(){
    //     document.getElementById("confirm_overlay").style.display = "block";
    // }


     document.addEventListener("DOMContentLoaded", function (event) {
         document.querySelectorAll(".niceCountryInputSelector").forEach(element => new NiceCountryInput(element).init());
     });



    function onChangeCallback(ctr) {
        const countryName = document.getElementById('countryname');
        console.log("country ; ",countryName.innerText);
        console.log("The country was changed: " + ctr);
        document.getElementById("country_hidden").value = ctr;
        if(countryName)
        document.getElementById("country_name_hidden").value = countryName.innerText;

    }
    document.addEventListener('DOMContentLoaded', () => {
        const result = document.getElementById("update_result").value;
        if(result) {
            updateResultPopup(result);
        }
    });
    
    function updateResultPopup(result) {

        // closeMoredetailsPopup();
        // document.getElementById("overlay").style.display = "block";
        document.getElementById("result_overlay").style.display = "block";

        const approveImgPath = '${pageContext.request.contextPath}/resourcesNew1/assets/submerchantregister/Successful.svg';
        const rejectImgPath = '${pageContext.request.contextPath}/resourcesNew1/assets/submerchantregister/Declined.svg';
        const result_popup_image = document.getElementById('result_popup_image');
        const result_popup_text = document.getElementById('result_popup_text');

        if (result === 'true') {
            result_popup_image.src = approveImgPath;
            result_popup_text.innerText = 'We have received your request. Please await approval.';
        } else if(result === 'false') {
            result_popup_image.src = rejectImgPath;
            result_popup_text.innerText = 'We have received your request. Error in technical Side';
        }
    }


</script>


	<script>

	

    function validateForm() {
        // Business Name validation
        var businessName = document.getElementById("businessName").value;
        if (businessName.trim() === "") {
            alert("Business Name is required.");
            return false;
        }

        // Email validation
        var email = document.getElementById("email").value;
        var emailPattern = /^[a-zA-Z0-9._-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,6}$/;
        if (email.trim() === "") {
            alert("Email is required.");
            return false;
        } else if (!emailPattern.test(email)) {
            alert("Please enter a valid email address.");
            return false;
        }

        // Website validation
        var website = document.getElementById("website").value;
        var websitePattern = /^https?:\/\/.+$/;
        if (website.trim() === "") {
            alert("Website is required.");
            return false;
        } else if (!websitePattern.test(website)) {
            alert("Please enter a valid URL (include http:// or https://).");
            return false;
        }

        // Industry validation
        var industry = document.getElementById("industry").value;
        if (industry.trim() === "") {
            alert("Industry is required.");
            return false;
        }

        var country = document.getElementById("country_hidden").value;
        var country_Name = document.getElementById('countryname');
        if (country.trim() === "") {
            document.getElementById("country_hidden").value = "MY"; // Default country code
            if(country_Name)
                document.getElementById("country_name_hidden").value = country_Name.innerText;
        }


        return true;
    }

    function openConfirmPopup() {
        if (validateForm()) {

            document.getElementById("confirm_overlay").style.display = "block";

        }
    }

    function submitDetails() {
        document.getElementById("confirm_overlay").style.display = "none";
        document.getElementById("overlay").style.display = "block";

        setTimeout(()=>{
            document.getElementById("businessdetailform").submit();
        }, 400);

    }
</script>



</body>
</html>