<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>

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
	font-size: 30px;
	line-height: 110%;
	margin: 0;
}

.invoiceidtext {
	font-size: 18px !important;
}

.statuscheckbtn {
	padding: 8px 22px;
	border-radius: 25px;
	background-color: #005baa;
	border: 2px solid #005baa !important;
	color: #fff;
	cursor: pointer;
	height:25%;
}

.invoiceidfield {
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

h5 {
	color: #333 !important;
	font-weight: 500 !important;
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

 @media (max-width: 767px){
 .d-flex{
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
  -ms-overflow-style: none;  /* IE and Edge */
  scrollbar-width: none;  /* Firefox */
}
</style>

</head>
<body>

	<div class="test" id="pop-bg-color"></div>
	<div class="container-fluid mb-0" id="main-container">

		<div class="row">
			<div class="col s12">
				<div class="card blue-bg text-white">
					<div class="card-content">
						<div class="d-flex align-items-center">
							<h3 class="text-white">
								<strong>Status Check</strong>
							</h3>
						</div>
					</div>
				</div>
			</div>
		</div>

		<!--  modal  -->

		<div class="modal fade bd-example-modal-xl pop-body"
			style="width: 500px !important; height: 270px !important;"
			id="exampleModalCenter" tabindex="-1" role="dialog"
			aria-labelledby="mySmallModalLabel" aria-hidden="true">
			<div class="modal-dialog modal-xl">
				<div class="modal-content "
					style="padding: 0 !important; margin: 0 !important;">
					<p class="pop-head"
						style="background-color: #f9f9f9; width: 100%; height: 60px; color: #005baa; padding-top: 12px; border-bottom: 2px solid #ffa500;">Information</p>
					<img
						src="${pageContext.request.contextPath}/resourcesNew1/assets/NoRecordPNG.png"
						width="60px !important; height:60px !important;">
					<p id="innerText"
						style="font-size: 22px; color: black; z-index: 3;"></p>
						
					<p id="innerTextHelp"
						style="font-size: 15px; color: black; z-index: 3;"></p>	

					<div class="modal-footer">
						<button type="button" class="btn btn-secondary"
							data-dismiss="modal" id="close" onclick="closepopup()"
							style="background-color: #005baa; width: 106px !important; height: 38px !important; font-size: 18px; border-radius: 50px !important; margin-right: 187px !important; letter-spacing: 0.8px; font-family: 'Poppins', sans-serif; font-weight: medium; transform: translateY(-10px);">Close</button>
					</div>
				</div>
			</div>
		</div>

		<!-- end : modal -->



		<div class="row">
			<div class="col s12">
				<div class="card blue-bg text-white">
					<div class="card-content status-check-cardcontent">
						<div class="d-flex lign-items-center">
							<h3 class="text-white">
								<strong>Check TNG and SPP Transaction Status</strong>
							</h3>
						</div>

						<div class="row invoiceidfield">

							<div class=" col s12 m12 l12 ">
								<br>
								<form class = "d-flex" style = "flex-direction:row; align-items:flex-end ;justify-content:flex-start;"
									action="${pageContext.request.contextPath}/payoutDataUser/getTngAndSppStatus"
									method="post">
									
									 <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
 
									<div class = "d-flex" style="flex-direction:column; width:55%; padding-right:8%">
									<label for="invoiceid" class="invoiceidtext">Invoice
										ID</label>
										
										<input type="text" placeholder="00000000000"
										name="invoiceid" id="invoiceid" value="">
									</div>

									<div class = "" style="width:45%">
									<button class="statuscheckbtn" type="submit">
										<font color="white">Check</font>
									</button>
									</div>
									
									</form>
							</div>

						</div>
					</div>
				</div>
			</div>
		</div>


		<div class="row">
			<div class="col s12">
				<div class="card blue-bg text-white">
					<div class="card-content d-flex responsecontent">
						<div class="d-flex align-items-center">
							<h3 class="text-white mb-2">
								<strong>Response</strong>
							</h3>

						</div>
						<div class="responsedatacontent">
							<div class="card-content padding-card responsecard">

								<div class="table-responsive m-b-20 m-t-15">
									<table id="data_list_table"
										class="table table-striped responsetable">
										<thead>
											<tr>
												<th>Response Code</th>
												<th>Response Message</th>
												<th>Amount</th>
												<!-- <th>MID</th>-->
												<!-- <th>Transaction Date</th> -->
												<th>Transaction Type</th>
												<th> Transaction Status</th>
											</tr>
										</thead>
										<tbody class="responsedata">

											<c:forEach items="${response.itemList}" var="dto"
												varStatus="loop">
												<c:if test="${loop.index > 0}">
												<tr class="dto-${loop.index}">
													<td>${dto.responseCode}</td>
													<td>${dto.responseMessage}</td>
													<td>${dto.responseData.amount}</td>
													<%-- <td>${dto.responseData.mid}</td>--%>
													<%--<td>${dto.responseData.transactionDate}</td>--%>
													<td>${dto.responseData.txnType}</td>
													<td>${dto.responseData.transactionStatus}</td>
												</tr>
												</c:if>
											</c:forEach>
										</tbody>
									</table>

								</div>
							</div>
						</div>
					</div>
				</div>
			</div>


		</div>

	</div>
	<script lang="JavaScript">
	
	
	const form = document.querySelector('form');

	  form.addEventListener('submit', (event) => {
	    const invoiceIdInput = document.querySelector('#invoiceid');

	    if (invoiceIdInput.value === '') {
	      event.preventDefault();
	      alert('Please Enter an invoice ID.');
	    }
	  });
	  
	  
	
	
			document.getElementById("pop-bg-color").style.display ="none";
								
			if (${not empty response.itemList}) {
					 
						
/* 					console.log("${response.itemList.get(0).responseCode}","${response.itemList.get(0).responseMessage}","${response.itemList[0].responseData.amount}"); 
 */			
				
 				if (${response.itemList.get(0).responseMessage == "FAILURE"}) {
						
					 document.getElementById("pop-bg-color").style.display ="block";
					 document.getElementById("exampleModalCenter").style.display = "block";
					 document.getElementById("data_list_table").style.display = "none";
			         
			         
			         document.getElementById("innerText").innerText = "${response.itemList[0].responseDescription}";
			         document.getElementById("innerText").style.fontWeight = "400";
			         document.getElementById("innerText").style.color = "#171717";
			         document.getElementById("nxt").style.cursor = "not-allowed";
			         document.getElementById("nxt").style.opacity = "0.6";
			         document.getElementById("nxt").disabled = "disabled";
			    	
			     }
 				/* else if(${response.itemList.get(0).responseMessage == "SUCCESS"})	{
 					
 					if(${empty response.itemList.get(0).responseData}){
 						 document.getElementById("pop-bg-color").style.display ="block";
 						 document.getElementById("exampleModalCenter").style.display = "block";
 						 document.getElementById("data_list_table").style.display = "none";
 				         
 				         
 				         document.getElementById("innerText").innerText = "${response.itemList[0].responseDescription}";
 				         document.getElementById("innerText").style.fontWeight = "400";
 				         document.getElementById("innerText").style.color = "#171717";
 				         document.getElementById("nxt").style.cursor = "not-allowed";
 				         document.getElementById("nxt").style.opacity = "0.6";
 				         document.getElementById("nxt").disabled = "disabled";
 					}
 					
 				} */
			}

			
			function closepopup(){
			 document.getElementById("exampleModalCenter").style.display ="none";
			  document.getElementById("pop-bg-color").style.display ="none";
			 }
		 
			</script>
</body>


<
</html>





