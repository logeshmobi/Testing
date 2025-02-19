
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




<style type="text/css">



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
		});
	});

	function openNewWin(merchantName, id) {

		$("#overlay").show(); 
		var topupAmount = document.getElementById("c_code").value;
		//var settlementAmount = document.getElementById("c_code1").value;
		var oldDepositAmount = document.getElementById("avableDepoAmount").value;
		document.getElementById("merchant-topup-btn").disabled = true;
		
		try {
			if ((topupAmount == null || topupAmount == '' || topupAmount == '.00')) {

				alert(" Enter the Deposit amount ..");
				throw new Error("Terminating code execution");
			} else if (topupAmount <= 5.00) {
				console.log("parse value : ", topupAmount);
				alert("Deposit amount Should be more than 5.00 RM");
				throw new Error("Terminating code execution");
			} else {
				document.location.href = '${pageContext.request.contextPath}/payoutDataUser/updateTopup?id='
						+ id
						+ '&topupAmount='
						+ topupAmount
						+ '&oldDepositAmount=' + oldDepositAmount;
				form.submit;
			}

			/* 	if (topupAmount == null || topupAmount == ''||topupAmount == '.00') {
					topupAmount = document.getElementById("avableDepoAmount").value;
					 
				//	alert("Please Enter the Topup amount..");
					
				} */

			/* if (topupAmount == null || topupAmount == '') {
				topupAmount = document.getElementById("avableDepoAmount").value;
				 
				alert("Please Enter the Topup amount.."+topupAmount);
				
			} else if (topupAmount == '.00') {
				alert("Enter Proper Deposit Amount.");
				throw new Error("Terminating code execution");
			}
			
			if (settlementAmount == null || settlementAmount == '') {
				settlementAmount = document.getElementById("avableSettleAmount").value;
				alert("Please Enter the Settlement amount.."+settlementAmount);
			} else if (settlementAmount == '.00') {
				alert("Enter Proper settlement Amount.");
				throw new Error("Terminating code execution");
			} */

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

							<div class="input-field col s12 m5 l5 businessnamecol" >


								<select name="merchantName" id="merchantName"
									path="merchantName"
									onchange="javascript:location.href = this.value;"
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

							<!-- Script -->
							<script>
								$(document).ready(function() {

									// Initialize select2
									$("#selUser").select2();
									$(".select-filter").select2();

								});
							</script>



							<div class="input-field col s12 m3 l3 select-search">

								<style>
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

									<label for="AMOUNT">Deposit Amount</label> <input type="text"
										placeholder="" name="avableAmount" id="avableDepoAmount"
										value="${currentAmount} " readonly="readonly">


								</div>


								<c:if test="${currentAmount != null}">
									<div class="input-field col s12 m6 l6 "
										style="text-align: center !important;">

										<label for="c_code">Add Deposit Amount</label> <input
											type="text" value="" placeholder="" id="c_code"
											name="depositamount" inputmode="decimal">
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

						<button class="merchant-topup-btn" type="button" id="merchant-topup-btn"
							onclick="openNewWin('${merchant.username}','${merchant.id }')">
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


</html>

