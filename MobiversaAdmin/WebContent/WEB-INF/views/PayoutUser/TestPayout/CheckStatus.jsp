<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@page import="com.mobiversa.payment.controller.PayoutUserController"%>

<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<%@taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>


<!DOCTYPE html>
<html>
<head>

<link rel="preconnect" href="https://fonts.googleapis.com">
<link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
<link
	href="https://fonts.googleapis.com/css2?family=Poppins:wght@300;400&display=swap"
	rel="stylesheet">



<meta charset="UTF-8">
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<meta
	content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no"
	name="viewport">

<title>Status Check</title>


<style>
h3 {
	font-size: 40px;
	/* 	line-height: 110%; */
	margin: 0;
}

.mobiapikey {
	font-size: 15px !important;
}

.statuscheckbtn {
	padding: 8px 22px;
	border-radius: 25px;
	background-color: #005baa;
	border: 2px solid #005baa !important;
	color: #fff;
	cursor: pointer;
	height: 25%;
}

.mobiapikey {
	display: flex !important;
	/*  flex-direction: row !important; */
	align-items: flex-end !important;
}

}
.btnfield {
	height: 100% !important;
}

.responsecontent {
	flex-direction: column !important;
	align-items: stretch !important;
	justify-content: space-between !important;
}

.responsedata {
	margin-bottom: 0px !important;
	margin-top: 10px !important;
}

input::placeholder {
	color: #9e9e9e !important;
}

.responsedatacontent {
	margin-left: 3px !important
}

#exampleModalCenter {
	z-index: 99;
	width: 25%;
	font-size: 24px;
	font-weight: 400;
	font-family: 'Poppins', sans-serif;
	text-align: center;
	border-radius: 25px;
}

.test {
	position: fixed;
	top: 0;
	left: 0;
	width: 100%;
	height: 100%;
	z-index: 90;
	background-color: rgba(0, 0, 0, 0.5);
}

.mb-0 {
	padding-bottom: 0px !important;
}

@media ( max-width : 767px) {
	.d-flex {
		display: flex;
	}
}

.innerText {
	white-space: nowrap;
}

.modal::-webkit-scrollbar {
	display: none;
}

/* Hide scrollbar for IE, Edge and Firefox */
.modal {
	-ms-overflow-style: none; /* IE and Edge */
	scrollbar-width: none; /* Firefox */
}

input::placeholder {
	font-size: 12px; /* Adjust the font size as needed */
}

input:not([type]), input[type=text]:not(.browser-default), input[type=password]:not(.browser-default),
	input[type=email]:not(.browser-default), input[type=url]:not(.browser-default),
	input[type=time]:not(.browser-default), input[type=date]:not(.browser-default),
	input[type=datetime]:not(.browser-default), input[type=datetime-local]:not(.browser-default),
	input[type=tel]:not(.browser-default), input[type=number]:not(.browser-default),
	input[type=search]:not(.browser-default), textarea.materialize-textarea
	{
	background-color: transparent !important;
	border: none !important;
	/* border-bottom: 1px solid #9e9e9e !important; */
	border-bottom: 1.5px solid #F5A623 !important;
	border-radius: 0;
	outline: none;
	height: 3rem;
	width: 90%;
	font-size: 16px;
	margin: 0 0 8px 0;
	padding: 0;
	box-shadow: none;
	box-sizing: content-box;
	transition: box-shadow .3s, border .3s;
}

.col .row {
	margin-left: -0.75rem;
	margin-right: -0.75rem;
	padding-left: 20px;
}
</style>

</head>


<body>



<script>
		function validateForm() {
			// Get form inputs

			var TxnIdorPayoutId = document.getElementById('txnIdorPayoutId').value;
			
			console.log(TxnIdorPayoutId);
			
			debugger;
			// Check if inputs are empty
			if (TxnIdorPayoutId === '' || TxnIdorPayoutId == "") {
				alert('Please fill Transaction Id or Payout Id ');
				return false;
			}

			// If all validations pass, allow the form to be submitted
			return true;
		}
	</script>


	<div class="test" id="pop-bg-color"></div>
	<div class="container-fluid mb-0" id="main-container">


		<div class="row">
			<div class="col s12">
				<div class="card blue-bg text-white">
					<div class="card-content">
						<div class="d-flex align-items-center">
							<h3 class="text-white">
								<strong>Payout Transaction Status</strong>
							</h3>
						</div>
					</div>
				</div>
			</div>
		</div>




		<div class="row">
			<div class="col s12">
				<div class="card blue-bg text-white">
					<div class="card-content status-check-cardcontent">
						<div class="d-flex align-items-center">
							<h5 style="padding-left: 20px">Required Fields</h5>
						</div>

						<div class="row sellerorderfield">


							<form action="${pageContext.request.contextPath}/testpayout/searchTxnStatus"
								method="post" onsubmit="return validateForm()">

								<div class="row">
									<div class="input-field col s12 m6 l6 ">
										<label for="Service" style="font-size: 17px;">Service</label>
										<input type="text" value="PAYOUT_TXN_DETAIL" name="Service"
											id="Service" readonly>
									</div>


									<div class="input-field col s12 m6 l6 ">
										<label for="BusRegNO" style="font-size: 17px;">Transaction
											ID or Payout ID</label> <input class="form-control" type="text"
											 placeholder="Enter Transaction Id or Payout Id" name="txnIdorPayoutId"
											id="txnIdorPayoutId">
									</div>



									<div class="statuscheckbtn-container"
										style="width: 45%; display: flex; justify-content: center; width: 95%;">
										<button class="statuscheckbtn" type="submit">
											<font color="white" style="font-size: 12px">Submit</font>
										</button>
									</div>



								</div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>


	<script>
		const form = document.querySelector('form');
		document.getElementById("pop-bg-color").style.display = "none";
	</script>

</body>
</html>