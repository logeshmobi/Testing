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
    input:not([type]), input[type=text]:not(.browser-default), input[type=password]:not(.browser-default), input[type=email]:not(.browser-default), input[type=url]:not(.browser-default), input[type=time]:not(.browser-default), input[type=date]:not(.browser-default), input[type=datetime]:not(.browser-default), input[type=datetime-local]:not(.browser-default), input[type=tel]:not(.browser-default), input[type=number]:not(.browser-default), input[type=search]:not(.browser-default), textarea.materialize-textarea {
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
    
    .card .card-content {
    padding: 5px;
    border-radius: 0 0 2px 2px;
}
 .transaction-details-box {
    border: 1.5px solid #F5A623; /* Black border */
    padding: 10px; /* Adjust the padding to control the size */
    width: 50%; /* Adjust the width as per your requirement */
    margin: 0 auto; /* Center the box horizontally */
    border-radius:10px;
}   

.card .card-content p {
     margin-bottom: 5px;
}
body {
    font-family: 'Poppins', sans-serif;
}

.detail-item {    
display: flex;     
justify-content: space-around;    
margin-bottom: 10px; 
}
 
 
.detail-item span {
    flex: 1;
    text-align: left;
}

.detail-item span:nth-child(2) {
    flex: 0.3; /* Adjust the width for the second span as needed */
}

.detail-item span:nth-child(3) {
    flex: 1.7; /* Adjust the width for the third span as needed */
}


</style>

</head>


<body>

	<div class="test" id="pop-bg-color"></div>
	<div class="container-fluid mb-0" id="main-container">


		<div class="row">
			<div class="col s12">
				<div class="card blue-bg text-white">
					<div class="card-content" style="padding-top:20px; padding-bottom:20px">
						<div class="d-flex align-items-center">
							<h3 class="text-white" style="padding-left:20px">
								<strong>Payout Transaction Request</strong>
							</h3>
						</div>
					</div>
				</div>
			</div>
		</div>




	<div class="row">
    <div class="col s12">
        <div class="card blue-bg text-white">
            <div class="card-content status-check-cardcontent" style="display: flex; justify-content: center; align-items: center;">
                <!-- Add the image element with the correct src attribute and set width and height -->
                <img class="img-fluid mobi-close" 
                     src="${pageContext.request.contextPath}/resourcesNew1/assets/Failure.png"
                     style="width: 70px; height: 70px;">
                     
                     
                <!-- Additional content can be added here -->
            </div>
            
         <div class="card-content status-check-cardcontent" style="text-align: center;">
                 <h6 style="text-align: center;">
                    <strong style="font-size: 20px !important; margin-top:0px;">"No transaction record matches the provided details."</strong><br>
                </h6>
            </div>
            
			
		
			        <div class="card blue-bg text-white">
			            <div class="card-content status-check-cardcontent">
			                <div class="transaction-details-box">
			                    <h6 style="font-size: 17px !important; margin-top: 0; color: #005baa; font-weight: 600;padding-bottom: 5px;">Transaction Details</h6>
							    <div class="detail-item"
												style=" margin-bottom: 5px;font-size: 13px !important; margin-top: 0; color: #707070;">
												<span style="min-width: 30%;">Response Code</span><span>:</span><span>${responseCode}</span>
											</div>
			                        <div class="detail-item"
												style=" margin-bottom: 5px;font-size: 13px !important; margin-top: 0; color: #707070;">
												<span style="min-width: 30%;">Response Message</span><span>:</span><span>${responseMessage}</span>
											</div>
	                                 <div class="detail-item"
												style="  margin-bottom: 5px;font-size: 13px !important; margin-top: 0; color: #707070;">
												<span style="min-width: 30%;">Response Description</span><span>:</span><span>${responseDescription}</span>
											</div>
											
											 <div class="detail-item"
												style="  margin-bottom: 5px;font-size: 13px !important; margin-top: 0; color: #707070;">
												<span style="min-width: 30%;">Failure Reason</span><span>:</span><span>${failureReason}</span>
											</div>
									
			                </div>
			                		  <div class="statuscheckbtn-container" style="width: 100%; display: flex; justify-content: center; width: 100%; margin-top:30px; margin-bottom:20px">
										<button class="statuscheckbtn" type="submit"  onclick="back()">
											<font color="white" style="font-size: 12px">Back</font>
										</button>
									</div>
			            </div>
			        </div>
			         
		
		
            
        </div>
    </div>
</div>


</div>


 <script>
        // Function to redirect to the specified URL
        function back() {
            window.location.href = "${pageContext.request.contextPath}/testpayout/checkStatus";
        }
    </script>
	<script>
	
	const form = document.querySelector('form');
	document.getElementById("pop-bg-color").style.display ="none";			
	
	</script>
	
</body>
</html>