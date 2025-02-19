<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>
<%@page
	import="com.mobiversa.payment.controller.MerchantWebUploadTCController"%>
<html lang="en-US">
<head>
<meta charset="UTF-8">
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<meta
	content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no"
	name="viewport">
<link
	href="https://cdnjs.cloudflare.com/ajax/libs/sweetalert/1.1.3/sweetalert.min.css"
	rel="stylesheet" />
<link
	href="https://fonts.googleapis.com/css2?family=Poppins:ital,wght@0,100;0,200;0,300;0,400;0,500;0,600;0,700;0,800;0,900;1,100;1,200;1,300;1,400;1,500;1,600;1,700;1,800;1,900&display=swap"
	rel="stylesheet">
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/2.1.1/jquery.min.js"></script>
<script
	src="https://cdnjs.cloudflare.com/ajax/libs/sweetalert/1.1.3/sweetalert.min.js"></script>


<style type="text/css">
@import
	url('https://fonts.googleapis.com/css2?family=Poppins:ital,wght@0,100;0,200;0,300;0,400;0,500;0,600;0,700;0,800;0,900;1,100;1,200;1,300;1,400;1,500;1,600;1,700;1,800;1,900&display=swap')
	;

body {
	font-family: "Poppins", sans-serif !important;
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

.businessnamecol .select2-container {
	background-color: #fff !important;
	padding: 6px !important;
	border: 2px solid #005baa !important;
	z-index: 0 !important;
	width: 100% !important;
}
</style>


<script type="text/javascript">
	window.history.replaceState({}, document.title, "/MobiversaAdmin/"
			+ "payoutDataUser/");
</script>


<script type="text/javascript">
	// my changes
	document.addEventListener('DOMContentLoaded', function() {
		var amountInput = document.getElementById('c_code');

		amountInput.addEventListener('input', function() {
			// Remove non-numeric and non-dot characters
			amountInput.value = amountInput.value.replace(/[^0-9]/g, '');

			// Parse the sanitized value as a float
			const amountValue = parseFloat(amountInput.value) / 100 || 0;

			// Format the float value as a string with two decimal places
			const formattedValue = amountValue.toFixed(2);

			console.log("value : ", formattedValue)

			// Update the input value with the formatted value
			amountInput.value = formattedValue;

			disabledSelectBtn();
		});

	});

	// my changes
	function openNewWin(merchantName, id) {
		var topupAmount = document.getElementById("c_code").value.replace(/,/g,
				'');
		console.log("top up amount checking isssssssssssssssssss "
				+ topupAmount);
		//var settlementAmount = document.getElementById("c_code1").value;
		var oldDepositAmount = document.getElementById("avableDepoAmount").value;
		var description = document.getElementById("commentsTextarea").value;
		var description1 = document.getElementById("commentsTextarea");
		var message = document.getElementById('characterLimitMessage');
		console.log("executing before");
		console.log(description.length);
		try {
			if ((topupAmount == null || topupAmount == '' || topupAmount == '.00')) {

				alert(" Enter the Deposit amount ..");
				//throw new Error("Terminating code execution");
			} else if (topupAmount <= 5.00) {
				console.log("parse value : ", topupAmount);
				alert("Deposit amount Should be more than 5.00 RM");

				//throw new Error("Terminating code execution");
			} else {
				closeConfirmationPopUp();
				document.location.href = '${pageContext.request.contextPath}/payoutDataUser/updateTopup?id='
						+ id
						+ '&topupAmount='
						+ topupAmount
						+ '&oldDepositAmount='
						+ oldDepositAmount
						+ '&comments=' + description;
				document.getElementById("merchant-topup-btn").disabled = true;
				$("#overlay").show();

				form.submit;
			}

		} catch (error) {
			console.log("An exception occurred: " + error.message);
		}
	}
</script>
<style>
.td {
	text-align: right;
}
</style>

<script lang="JavaScript">
	function submitForms() {
		document.getElementById("uploadForm").submit();
		/* alert(document.getElementById("mailFile").value);  */
		alert('Input can not be left blank');
		if ($('#mailFile').val() == '') {

		}
	}
</script>
<script type="text/javascript">
	$().ready(function() {

		$('#mailFile').on('change', function() {
			myfile = $(this).val();
			var ext = myfile.split('.').pop();
			if (ext == "pdf") {
				return true;
			} else {
				alert("Please enter valid pdf file..");
				document.getElementById("mailFile").value = "";

				return false;
			}

		});
	});
</script>


<script lang="JavaScript">
	$(document).ready(function() {

		$("#c_code").change(function() {

			ValidateAmount();
		});
		$("#c_code1").change(function() {

			ValidateAmount1();
		});

	});

	function ValidateAmount() {
		var amount = document.getElementById("c_code").value;

		if (amount.includes(",")) {

			amount = amount.replace(/,/g, "");

		}
		if (!isNaN(amount)) {
			if (amount.includes(".")) {

				var array1 = [];
				array1 = amount.split(".");
				//alert("array1[1]: "+array1[1]);
				if (array1[0] == '' || array1[0] == null) {
					array1[0] = "0";
				}
				if (array1[1].length >= 2) {
					amount = array1[0] + "." + array1[1].substring(0, 2);
					//alert("Yours Requested Amount to do Transaction "+amount);
					document.getElementById("c_code").value = amount;
					return true;

				} else if (array1[1].length >= 1) {
					amount = amount + "0";
					//alert("Yours Requested Amount to do Transaction "+amount);
					document.getElementById("c_code").value = amount;
					return true;

				} else {
					amount = amount + "00";
					// alert("Yours Requested Amount to do Transaction "+amount);
					document.getElementById("c_code").value = amount;
					return true;
				}

			} else {

				amount = amount + ".00";
				// alert("Yours Requested Amount to do Transaction "+amount);
				document.getElementById("c_code").value = amount;
				return true;

			}
		} else {

			alert("Enter Proper Amount.");
			document.getElementById("c_code").focus();
			return false;
		}

	}

	function ValidateAmount1() {
		var amount = document.getElementById("c_code1").value;

		if (amount.includes(",")) {

			amount = amount.replace(/,/g, "");

		}
		if (!isNaN(amount)) {
			if (amount.includes(".")) {

				var array1 = [];
				array1 = amount.split(".");
				//alert("array1[1]: "+array1[1]);
				if (array1[0] == '' || array1[0] == null) {
					array1[0] = "0";
				}
				if (array1[1].length >= 2) {
					amount = array1[0] + "." + array1[1].substring(0, 2);
					//alert("Yours Requested Amount to do Transaction "+amount);
					document.getElementById("c_code1").value = amount;
					return true;

				} else if (array1[1].length >= 1) {
					amount = amount + "0";
					//alert("Yours Requested Amount to do Transaction "+amount);
					document.getElementById("c_code1").value = amount;
					return true;

				} else {
					amount = amount + "00";
					// alert("Yours Requested Amount to do Transaction "+amount);
					document.getElementById("c_code1").value = amount;
					return true;
				}

			} else {

				amount = amount + ".00";
				// alert("Yours Requested Amount to do Transaction "+amount);
				document.getElementById("c_code1").value = amount;
				return true;

			}
		} else {

			alert("Enter Proper Amount.");
			document.getElementById("c_code1").focus();
			return false;
		}

	}
</script>


<script>
	$(function() {
		// bind change event to select
		$('#merchantName').on('change', function() {
			var url = $(this).val(); // get selected value
			//alert(url);
			if (url) { // require a URL
				window.location = url; // redirect
				// alert(window.location);
			}
			return false;
		});
	});
</script>
<!-- <script lang="JavaScript">
    $().ready(function() {
    $("#mailFile").click(function(){
        $("p").hide();
    });
    });
    </script> -->

</head>

<body>
	<div id="overlay">
		<div id="overlay_text">
			<img class="img-fluid"
				src="${pageContext.request.contextPath}/resourcesNew1/assets/loader.gif">
		</div>
	</div>
	<div class="container-fluid">
		<div class="row">
			<div class="col s12">
				<div class="card blue-bg text-white">
					<div class="card-content">
						<div class="d-flex align-items-center">
							<h3 class="text-white">
								<strong>Merchant List </strong>
							</h3>
						</div>


					</div>
				</div>
			</div>
		</div>

		<div class="row">
			<div class="col s12">
				<div class="card border-radius">
					<div class="card-content padding-card">
						<div class="row">
							<div class="input-field col s12 m3 l3">Business Name</div>

							<div class="input-field col s12 m5 l5 businessnamecol">


								<select name="merchantName" id="merchantName"
									path="merchantName"
									onchange="javascript:location.href = this.value; disabledSelectBtn();"
									class="browser-default select-filter">
									<!-- onclick="javascript: locate();"> -->
									<optgroup label="Business Names" style="width: 100%">
										<option selected value=""><c:out value="businessName" /></option>

										<c:forEach items="${merchant1}" var="merchant1">
											<c:if
												test="${merchant1.mid.mid!=null || merchant1.mid.motoMid!=null || merchant1.mid.ezypassMid!=null
								|| merchant1.mid.ezywayMid!=null || merchant1.mid.ezyrecMid!=null || merchant1.mid.umMotoMid != null 
								 || merchant1.mid.umEzyrecMid != null || merchant1.mid.umEzywayMid != null || merchant1.mid.umEzypassMid != null
								  || merchant1.mid.umMid != null || merchant1.mid.splitMid!=null || merchant1.mid.boostMid!=null || merchant1.mid.grabMid!=null || merchant1.mid.fpxMid}">
												<option
													value="${pageContext.request.contextPath}/payoutDataUser/merchantDetails?id=${merchant1.id}">
													${merchant1.businessName}
													<c:choose>
														<c:when test="${merchant1.mid.mid!=null}">
                                                        ~${merchant1.mid.mid }
                                                    </c:when>
														<c:when test="${merchant1.mid.motoMid!=null}">
                                                        ~${merchant1.mid.motoMid}
                                                    </c:when>
														<c:when test="${merchant1.mid.ezywayMid!=null}">
                                                        ~${merchant1.mid.ezywayMid}
                                                    </c:when>
														<c:when test="${merchant1.mid.ezyrecMid!=null}">
                                                        ~${merchant1.mid.ezyrecMid}
                                                    </c:when>
														<c:when test="${merchant1.mid.splitMid!=null}">
                                                        ~${merchant1.mid.splitMid}
                                                    </c:when>
														<c:when test="${merchant1.mid.umMotoMid!=null}">
                                                        ~${merchant1.mid.umMotoMid}
                                                    </c:when>
														<c:when test="${merchant1.mid.umEzyrecMid!=null}">
                                                        ~${merchant1.mid.umEzyrecMid}
                                                    </c:when>
														<c:when test="${merchant1.mid.umEzywayMid!=null}">
                                                        ~${merchant1.mid.umEzywayMid}
                                                    </c:when>
														<c:when test="${merchant1.mid.umEzypassMid!=null}">
                                                        ~${merchant1.mid.umEzypassMid}
                                                    </c:when>
														<c:when test="${merchant1.mid.umMid!=null}">
                                                        ~${merchant1.mid.umMid}
                                                    </c:when>

														<c:when test="${merchant1.mid.boostMid!=null}">
                                                        ~${merchant1.mid.boostMid}
                                                    </c:when>

														<c:when test="${merchant1.mid.grabMid!=null}">
                                                        ~${merchant1.mid.grabMid}
                                                    </c:when>

														<c:when test="${merchant1.mid.fpxMid!=null}">
                                                        ~${merchant1.mid.fpxMid}
                                                    </c:when>

														<c:otherwise>
                                                        ~${merchant1.mid.ezypassMid}
                                                    </c:otherwise>
													</c:choose>


												</option>

											</c:if>

										</c:forEach>
									</optgroup>
								</select>
							</div>

						</div>

						<div class="row">
							<div class="input-field col s12 m3 l3"></div>


							<style>
#commentsTextarea::placeholder {
	color: #BEBEBE;
}
</style>


							<div id="confirmation_email_id" class="confirmation_email_class">
								<div class="confirmation_email_content_class">
									<div>
										<div
											style="padding: 15px 24px; text-align: center; font-size: 16px; color: #005baa; font-weight: 500; border-bottom: 2px solid orange;">
											Confirmation</div>
										<div class="confirmation_icon_div"
											style="display: flex; justify-content: center; margin-top: 8px">
											<img class="confirmation_icon_img"
												src="${pageContext.request.contextPath}/resourcesNew1/assets/confirmation.svg">
										</div>
										<div style="padding: 15px 30px; text-align: center;">
											<p id="amount_p" style="color: #586570"></p>
											<p style="color: #586570">Kindly specify the reason for
												this deposit.</p>
											<%--                                        <textarea id="commentsTextarea"  style=--%>
											<%--                                                "width: 100%;--%>
											<%--															min-height: 6rem;--%>
											<%--															background-color: transparent;--%>
											<%--															resize: none;--%>
											<%--															border-color: #ADADAD;--%>
											<%--															border-radius: 6px;--%>
											<%--															margin-top: 4px;--%>
											<%--															height: auto;--%>
											<%--															">--%>
											<%--										</textarea>--%>
											<textarea placeholder="Type reason here...(Mandatory)"
												id="commentsTextarea" required
												style="width: 100%; min-height: 6rem; background-color: transparent; color: #586570; resize: none; border-color: #ADADAD; border-radius: 6px; margin-top: 4px; height: auto;"
												oninput="limitTextareaCharacters()">
</textarea>
											<div id="characterLimitMessage"
												style="color: red; display: flex; justify-content: center; gap: 7px"></div>


										</div>
										<div
											style="padding: 10px; display: flex; border-radius: 12px; justify-content: center; background-color: #EFF8FF; gap: 20px;">

											<button type="button" class="confirmation_cancel_btn"
												onclick="closeConfirmationPopUp()">Cancel</button>
											<button disabled type="button"
												class="confirmation_deposit_btn" style="opacity: 0.5"
												onclick="openNewWin('${merchant.username}','${merchant.id }')"
												${currentAmount == 'N/A' ? 'disabled' : ''}>
												Deposit</button>
										</div>
									</div>


								</div>

							</div>


							<%--							email sent or not--%>


							<div id="successMailId" class="confirmation_email_class">
								<div class="confirmation_email_content_class">
									<div>
										<div
											style="padding: 15px 24px; text-align: center; font-size: 16px; color: #005baa; font-weight: 500">
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


							<%--                        <script>--%>
							<%--                            function limitTextareaCharacters() {--%>
							<%--                                var textarea = document.getElementById('commentsTextarea');--%>
							<%--                                var message = document.getElementById('characterLimitMessage');--%>
							<%--                                console.log(textarea.value.length)--%>

							<%--                                if (textarea.value.length > 100) {--%>
							<%--                                    // alert("exceeded")--%>
							<%--                                    textarea.value = textarea.value.slice(0, 100);--%>
							<%--                                    message.textContent = 'Only 100 characters are allowed';--%>
							<%--                                } else {--%>
							<%--                                    message.textContent = '';--%>
							<%--                                }--%>
							<%--                            }--%>
							<%--                        </script>--%>



							<script>
								function limitTextareaCharacters() {
									var textarea = document
											.getElementById('commentsTextarea');
									var message = document
											.getElementById('characterLimitMessage');
									var inputValue = textarea.value;
									// var sanitizedValue = inputValue.replace(/[^\w\s]/gi, '');
									var sanitizedValue = inputValue.replace(
											/[^\w\s,.]/gi, ''); // Allow commas and dots
									var btn = document
											.querySelector(".confirmation_deposit_btn");
									// Remove special characters
									console.log(inputValue.length == 0);

									if (inputValue.length == 0) {
										console.log("coming inside");
										btn.disabled = true;
										btn.style.opacity = "0.5";
										return;
									}

									if (inputValue.trim().length == 0) {
										btn.disabled = true;
										btn.style.opacity = "0.5";
										message.innerHTML = '<img src="${pageContext.request.contextPath}/resourcesNew1/assets/topUpErrorLine.svg" alt="Error" style="height: 22px;">'
												+ ' Only spaces are not allowed';
										return;
									}

									if (inputValue !== sanitizedValue) {
										// Special characters were found
										textarea.value = sanitizedValue; // Update textarea value without special characters
										message.innerHTML = '<img src="${pageContext.request.contextPath}/resourcesNew1/assets/topUpErrorLine.svg" alt="Erro style="height: 22px;">'
												+ ' Special characters are not allowed';
										btn.disabled = true;
										btn.style.opacity = "0.5";
									} else if (inputValue.length > 100) {
										textarea.value = inputValue.slice(0,
												100); // Limit characters
										message.innerHTML = '<img src="${pageContext.request.contextPath}/resourcesNew1/assets/topUpErrorLine.svg" alt="Error style="height: 22px;">'
												+ ' Only 100 characters allowed';
										btn.disabled = true;
										btn.style.opacity = "0.5";
									} else {
										message.textContent = '';
										btn.disabled = false;
										btn.style.opacity = "1";
									}
								}

								document
										.getElementById('commentsTextarea')
										.addEventListener(
												'blur',
												function() {
													var btn = document
															.querySelector(".confirmation_deposit_btn");
													document
															.getElementById('characterLimitMessage').textContent = '';
													// btn.disabled=false;
													// btn.style.opacity="1";
													var textarea = document
															.getElementById('commentsTextarea');
													var message = document
															.getElementById('characterLimitMessage');
													var inputValue = textarea.value;
													if (inputValue.length === 0) {
														// Textarea is empty, disable button
														btn.disabled = true;
														btn.style.opacity = "0.5";
													}
												});
							</script>



							<%--                        <script>--%>
							<%--                            function limitTextareaCharacters() {--%>
							<%--                                var textarea = document.getElementById('commentsTextarea');--%>
							<%--                                var message = document.getElementById('characterLimitMessage');--%>
							<%--                                var button = document.querySelector('.confirmation_deposit_btn');--%>
							<%--                                var value = textarea.value;--%>
							<%--                                var filteredValue = value.replace(/[^a-zA-Z0-9 .,]/g, '');--%>

							<%--                                if (value !== filteredValue) {--%>
							<%--                                    message.textContent = 'Only letters, numbers, spaces, dots, and commas are allowed';--%>
							<%--                                    button.disabled = true;--%>
							<%--                                    button.style.opacity="0.5";--%>
							<%--                                } else if (filteredValue.length > 100) {--%>
							<%--                                    textarea.value = filteredValue.slice(0, 100);--%>
							<%--                                    message.textContent = 'Only 100 characters are allowed';--%>
							<%--                                    button.disabled = false;--%>
							<%--                                } else {--%>
							<%--                                    message.textContent = '';--%>
							<%--                                    button.disabled = false;--%>
							<%--                                    button.style.opacity="1";--%>
							<%--                                }--%>
							<%--                            }--%>
							<%--                        </script>--%>





							<!-- Script -->
							<script>
								$(document).ready(function() {

									// Initialize select2
									$("#selUser").select2();
									$(".select-filter").select2();

								});

								function closeConfirmationPopUp() {
									body.style.paddingRight = 0;
									body.style.overflow = initialOverflow;
									var modal = document
											.getElementById("confirmation_email_id");
									modal.style.display = "none";

								}

								function closeSuccessEmail() {
									var modal = document
											.getElementById("successMailId");
									modal.style.display = "none";
								}

								function disabledSelectBtn() {

									var submitBtn = "${currentAmount}";
									var selectOpt = document
											.getElementById("merchantname");
									var depositamountOpt = document
											.getElementById("c_code");
									var selectedValue = selectOpt ? selectOpt.value
											: '';
									var selectedValue1 = depositamountOpt ? depositamountOpt.value
											: '';
									var selectOptBtn = document
											.getElementById("merchant-topup-btn");

									console
											.log("------------ "
													+ selectedValue);
									console.log("------------ "
											+ selectedValue1);
									const amountValue = parseFloat(selectedValue1) || 0.00;
									const formattedValue = amountValue
											.toFixed(2);

									const value_min = 10;

									const min_amount = parseFloat(value_min)
											.toFixed(2);

									console
											.log(
													" min : ",
													min_amount,
													formattedValue
															+ " : "
															+ (formattedValue > min_amount)
															+ " : "
															+ (selectedValue1 !== null)
															+ " : "
															+ (selectedValue !== ''));

									// if (selectedValue !== '' && selectedValue1 !== '' && formattedValue >= min_amount) {
									if (selectedValue !== ''
											&& selectedValue1 !== ''
											&& formattedValue >= min_amount
											&& submitBtn !== "N/A") {
										selectOptBtn.disabled = false;
										selectOptBtn.style.opacity = "1";
									} else {
										selectOptBtn.disabled = true;
										selectOptBtn.style.opacity = "0.5";
									}

								}
							</script>


							<div class="input-field col s12 m3 l3 select-search">

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


							</div>

						</div>
					</div>

				</div>
			</div>
		</div>

		<div class="row">
			<div class="col s12">
				<div class="card border-radius">
					<div class="card-content padding-card">


						<p id="msg" name="msg" style="color: #1B0AEA; font-size: 20px;">
							<b>${requestScope.responseData}</b>
						</p>

						<div class="d-flex align-items-center">
							<h5>Search Merchant Details</h5>
						</div>


						<div class="row">


							<%-- <div class="input-field col s12 m6 l6 ">
                            <label for="Email">USER NAME</label> <input type="text"
                                placeholder="username" name="username" id="username"
                                value="${merchant.username}" readonly="readonly"
                                path="username">

                        </div> --%>

							<div class="input-field col s12 m6 l6 ">
								<label for="Name">MERCHANT NAME</label> <input type="text"
									placeholder="merchantname" name="merchantname"
									id="merchantname" value="${merchant.businessName}"
									readonly="readonly" path="merchantname">

							</div>

							<%-- 	<c:if test="${merchant.mid.mid!=null}">
                            <div class="input-field col s12 m6 l6 ">

                                <label for="MID">EZYWIRE MID</label> <input
                                    class="form-control" type="text" placeholder="mid" name="mid"
                                    id="mid" path="mid" value="${merchant.mid.mid}"
                                    readonly="readonly">

                            </div>

                        </c:if>
                        <c:if test="${merchant.mid.motoMid!=null}">
                            <div class="input-field col s12 m6 l6 ">

                                <label for="MID">EZYMOTO MID</label> <input
                                    class="form-control" type="text" placeholder="motoMid"
                                    name="motoMid" id="motoMid" path="motoMid"
                                    value="${merchant.mid.motoMid}" readonly="readonly">

                            </div>

                        </c:if>
                        <c:if test="${merchant.mid.ezyrecMid!=null}">
                            <div class="input-field col s12 m6 l6 ">


                                <label for="MID">EZYREC MID</label> <input class="form-control"
                                    type="text" placeholder="ezyrecMid" name="ezyrecMid"
                                    id="ezyrecMid" path="ezyrecMid"
                                    value="${merchant.mid.ezyrecMid}" readonly="readonly">

                            </div>

                        </c:if>
                        <c:if test="${merchant.mid.ezywayMid!=null}">
                            <div class="input-field col s12 m6 l6 ">


                                <label for="MID">EZYWAY MID</label> <input class="form-control"
                                    type="text" placeholder="ezywayMid" name="ezywayMid"
                                    id="ezywayMid" path="ezywayMid"
                                    value="${merchant.mid.ezywayMid}" readonly="readonly">

                            </div>

                        </c:if>
                        <c:if test="${merchant.mid.ezypassMid!=null}">
                            <div class="input-field col s12 m6 l6 ">

                                <label for="MID">EZYPASS MID</label> <input
                                    class="form-control" type="text" placeholder="ezypassMid"
                                    name="ezypassMid" id="ezypassMid" path="ezypassMid"
                                    value="${merchant.mid.ezypassMid}" readonly="readonly">

                            </div>

                        </c:if>


                        <c:if test="${merchant.mid.splitMid!=null}">
                            <div class="input-field col s12 m6 l6 ">

                                <label for="MID">SPLIT MID</label> <input class="form-control"
                                    type="text" placeholder="splitMid" name="ezysplit"
                                    id="ezysplit" path="splitMid" value="${merchant.mid.splitMid}"
                                    readonly="readonly">

                            </div>
                        </c:if>

                        <c:if test="${merchant.mid.umMid!=null}">
                            <div class="form-group col-md-4">
                                <div class="form-group">

                                    <label for="MID">UMOBILE EZYPASS MID</label> <input
                                        class="form-control" type="text" placeholder="mid" name="mid"
                                        id="mid" path="mid" value="${merchant.mid.ezypassMid}"
                                        readonly="readonly">

                                </div>
                            </div>
                        </c:if>
                        <c:if test="${merchant.mid.umMid!=null}">
                            <div class="input-field col s12 m6 l6 ">

                                <label for="MID">UM-EZYWIRE MID</label> <input
                                    class="form-control" type="text" placeholder="umMid"
                                    name="umMid" id="umMid" path="umMid"
                                    value="${merchant.mid.umMid}" readonly="readonly">

                            </div>

                        </c:if>
                        <c:if test="${merchant.mid.umMotoMid!=null}">
                            <div class="input-field col s12 m6 l6 ">

                                <label for="MID">UM-EZYMOTO MID</label> <input
                                    class="form-control" type="text" placeholder="umMotoMid"
                                    name="umMotoMid" id="umMotoMid" path="umMotoMid"
                                    value="${merchant.mid.umMotoMid}" readonly="readonly">

                            </div>

                        </c:if>
                        <c:if test="${merchant.mid.umEzypassMid!=null}">
                            <div class="input-field col s12 m6 l6 ">

                                <label for="MID">UM-EZYPASS MID</label> <input
                                    class="form-control" type="text" placeholder="umEzypassMid"
                                    name="umEzypassMid" id="umEzypassMid" path="umEzypassMid"
                                    value="${merchant.mid.umEzypassMid}" readonly="readonly">


                            </div>
                        </c:if>
                            <c:if test="${merchant.mid.umEzyrecMid!=null}">
                            <div class="form-group col-md-4">
                                <div class="form-group">

                                    <label for="MID">UM-EZYREC MID</label> <input
                                        class="form-control" type="text" placeholder="umEzyrecMid" name="umEzyrecMid"
                                        id="umEzyrecMid" path="umEzyrecMid" value="${merchant.mid.umEzyrecMid}"
                                        readonly="readonly">

                                </div>
                            </div>
                        </c:if>

                        <c:if test="${merchant.mid.umEzyrecMid!=null}">
                            <div class="input-field col s12 m6 l6 ">

                                <label for="MID">UM-EZYREC MID</label> <input
                                    class="form-control" type="text" placeholder="umEzyrecMid"
                                    name="umEzyrecMid" id="umEzyrecMid" path="umEzyrecMid"
                                    value="${merchant.mid.umEzyrecMid}" readonly="readonly">


                            </div>
                        </c:if>


                        <c:if test="${merchant.mid.umEzywayMid!=null}">
                            <div class="input-field col s12 m6 l6 ">

                                <label for="MID">UM-EZYWAY MID</label> <input
                                    class="form-control" type="text" placeholder="umEzywayMid"
                                    name="umEzywayMid" id="umEzywayMid" path="umEzywayMid"
                                    value="${merchant.mid.umEzywayMid}" readonly="readonly">


                            </div>
                        </c:if>

                        <c:if test="${merchant.mid.boostMid!=null}">
                            <div class="input-field col s12 m6 l6 ">

                                <label for="MID">BOOST MID</label> <input class="form-control"
                                    type="text" placeholder="BoostMid" name="BoostMid"
                                    id="BoostMid" path="BoostMid" value="${merchant.mid.boostMid}"
                                    readonly="readonly">


                            </div>
                        </c:if>




                        <c:if test="${merchant.mid.grabMid!=null}">
                            <div class="input-field col s12 m6 l6 ">

                                <label for="MID">GRAB MID</label> <input class="form-control"
                                    type="text" placeholder="GrabMid" name="GrabMid" id="GrabMid"
                                    path="GrabMid" value="${merchant.mid.grabMid}"
                                    readonly="readonly">


                            </div>
                        </c:if>


                        <c:if test="${merchant.mid.fpxMid!=null}">
                            <div class="input-field col s12 m6 l6 ">

                                <label for="MID">FPX MID</label> <input class="form-control"
                                    type="text" placeholder="FpxMid" name="FpxMid" id="FpxMid"
                                    path="FpxMid" value="${merchant.mid.fpxMid}"
                                    readonly="readonly">


                            </div>
                        </c:if> --%>

							<c:if test="${currentAmount != null}">
								<div class="input-field col s12 m6 l6 ">

									<label for="AMOUNT">Available Balance</label> <input
										type="text" placeholder="" name="avableAmount"
										id="avableDepoAmount" value="${currentAmount} "
										readonly="readonly">
								</div>


								<c:if test="${currentAmount != null}">




									<div class="input-field col s12 m6 l6 "
										style="text-align: center !important;">

										<label for="c_code">Add Deposit Amount</label> <input
											type="text" value="" placeholder="" id="c_code"
											class="comma_seperater" name="depositamount"
											inputmode="decimal" oninput="formatNumber(this)">
									</div>
								</c:if>
							</c:if>
							<!-- old Method -->
							<%--

                         <c:if test="${currentAmount != null}">
                            <div class="input-field col s12 m6 l6 " style="text-align:center !important;" >

                                <label for="TOPUP_AMOUNT">Add Deposit Amount</label> <input
                                    type="text" value="" placeholder="" id="c_code" oninput="validateAmount()">

                            </div>

                        </c:if>  --%>

							<%-- <c:if test="${netAmt != null}">
                            <div class="input-field col s12 m6 l6 ">

                                <label for="SETT_AMOUNT">Settlement Amount</label> <input type="text"
                                    placeholder="" name="avableAmount" id="avableSettleAmount"
                                    value="${netAmt} " readonly="readonly">


                            </div>
                        </c:if> --%>

							<%-- <c:if test="${netAmt != null}">
                            <div class="input-field col s12 m6 l6 ">

                                <label for="Settlement">Add settlement Amount</label> <input
                                    type="text" value="" placeholder="" id="c_code1">

                            </div>

                        </c:if> --%>
						</div>

						<%--						<button class="merchant-topup-btn" type="button"
                                                id="merchant-topup-btn"
                                                onclick="openNewWin('${merchant.username}','${merchant.id }')">
                                                <font color="white">Submit</font>
                                            </button>
                    	--%>



						<button class="merchant-topup-btn" type="button"
							id="merchant-topup-btn" onclick="confirmationDialog()"<%--onclick="openNewWin('${merchant.username}','${merchant.id }')" ${currentAmount == 'N/A' ? 'disabled' : ''}--%>>
							<font color="white">Submit</font>
						</button>


					</div>
				</div>
			</div>
		</div>
	</div>


	<%--  </form> --%>

</body>


<script>
	// function formatNumber(input) {
	// alert(input.value)
	//     let value = input.value.replace(/[^0-9.]/g, '');
	//     let parts = value.split('.');
	//     let integerPart = parts[0];
	//     let decimalPart = parts.length > 1 ? '.' + parts[1] : '';
	//     integerPart = integerPart.replace(/\B(?=(\d{3})+(?!\d))/g, ',');
	//     input.value = integerPart + decimalPart;
	// }

	function formatNumber(input) {
		let value = input.value.replace(/[^0-9.]/g, '');
		let parts = value.split('.');
		let integerPart = parts[0];
		let decimalPart = parts.length > 1 ? '.' + parts[1] : '';
		integerPart = integerPart.replace(/\B(?=(\d{3})+(?!\d))/g, ',');
		var selectedValueDiv = document.getElementById("amount_p");
		/*if (selectedValueDiv) {
		    selectedValueDiv.innerHTML = "RM "+integerPart+".00";
		}*/
		input.value = integerPart + decimalPart;
		if (selectedValueDiv) {
			selectedValueDiv.innerHTML = "RM " + input.value;
		}
	}

	document.addEventListener('input', function(event) {
		let target = event.target;
		// Check if the input field needs formatting
		if (target.classList.contains('comma_seperater')) {
			formatNumber(target);
		}
	});

	document.addEventListener('click', function(event) {
		let inputs = document.querySelectorAll('.comma_seperater');
		inputs.forEach(function(input) {
			// Check if the input value needs formatting
			if (!input.value.includes(',')) {
				formatNumber(input);
			}
		});
	});

	// document.addEventListener('input', function(event) {
	//     if (event.target.classList.contains('comma_seperater')) {
	//         formatNumber(event.target);
	//     }
	// });

	function confirmationDialog() {
		var scrollbarWidth = window.innerWidth
				- document.documentElement.clientWidth;
		body.style.paddingRight = scrollbarWidth + "px";
		body.style.overflow = "hidden";
		var modal = document.getElementById("confirmation_email_id");
		modal.style.display = "block";
	}

	function sample1(merchantName, id) {
		console.log("merchant is :" + merchantName + " " + "id is " + id)
	}

	// var selectOpt = document.getElementById("merchantname");
	// var depositamountOpt = document.getElementById("depositamount");
	// var selectedValue = selectOpt ? selectOpt.value : '';
	// var selectedValue1 = depositamountOpt ? depositamountOpt.value : '';
	// var selectOptBtn = document.getElementById("merchant-topup-btn");
	//
	// console.log("------------ " + selectedValue);
	// console.log("------------ " + selectedValue1);
	// if (selectedValue !== '' && selectedValue1 !== null && selectedValue1 !== '') {
	// 	selectOptBtn.disabled = false;
	// 	selectOptBtn.style.opacity = "1";
	// } else {
	// 	selectOptBtn.disabled = true;
	// 	selectOptBtn.style.opacity = "0.5";
	// }

	disabledSelectBtn();

	function validateAmount() {
		// Get the input element
		var amountInput = document.getElementById('c_code');

		// Remove non-numeric characters from the input value
		var sanitizedValue = amountInput.value.replace(/[^0-9.]/g, '');

		// Set the sanitized value back to the input field
		amountInput.value = sanitizedValue;
	}
</script>

<script type="text/javascript">
	jQuery(document).ready(function() {
		$('#merchantName').select2();

	});
</script>

<style>
.merchant-topup-btn {
	padding: 8px 20px;
	border-radius: 10px;
	background-color: #005baa;
	border: 2px solid #005baa !important;
	color: #fff;
	margin: auto;
	display: table;
}
</style>


<input type="hidden" id="isEmailTriggered">


<%--<script>--%>
<%--    window.onload = function () {--%>
<%--        var textarea = document.getElementById('commentsTextarea');--%>
<%--        textarea.value = 'Type reason here..........';--%>

<%--        textarea.addEventListener('focus', function () {--%>
<%--            if (this.value === 'Type reason here..........') {--%>
<%--                this.value = '';--%>
<%--                this.classList.remove('gray-text');--%>
<%--            }--%>
<%--        });--%>

<%--        textarea.addEventListener('blur', function () {--%>
<%--            if (this.value === '') {--%>
<%--                this.value = 'Type reason here..........';--%>
<%--                this.classList.add('gray-text');--%>
<%--            }--%>
<%--        });--%>


<%--        textarea.addEventListener('input', function () {--%>
<%--            if (this.value.trim() !== '') {--%>
<%--                this.style.color = 'black';--%>
<%--            } else {--%>
<%--                this.style.color = 'gray';--%>
<%--            }--%>
<%--        });--%>

<%--        // Initial check for color--%>
<%--        if (textarea.value.trim() !== '') {--%>
<%--            textarea.style.color = '#C2C2C2';--%>
<%--        } else {--%>
<%--            textarea.style.color = 'gray';--%>
<%--        }--%>

<%--    };--%>
<%--</script>--%>


<style>
.gray-text {
	color: gray;
}

::placeholder {
	color: gray;
}
</style>

<script>
	var isEmailTriggered = document.getElementById("isEmailTriggered").value = "${isEmailSuccess}";

	console.log("email :::: " + isEmailTriggered);

	if (isEmailTriggered === "true") {
		var modal = document.getElementById("successMailId");
		if (modal) {
			modal.style.display = "block";

			var ptag = document.getElementById("emailTrueOrFalse");
			if (ptag) {
				ptag.innerHTML = "Email has been successfully sent !";
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

	var body = document.body;
	var initialOverflow = body.style.overflow;
</script>


</html>

