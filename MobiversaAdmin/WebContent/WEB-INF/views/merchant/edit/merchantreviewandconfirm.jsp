<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@page import="com.mobiversa.payment.controller.MerchantWebController"%>
<%@page import="com.mobiversa.common.bo.Merchant"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<head>


<meta charset="UTF-8">
<meta
	content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no"
	name="viewport">
<link
	href="https://cdnjs.cloudflare.com/ajax/libs/sweetalert/1.1.3/sweetalert.min.css"
	rel="stylesheet" />
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/2.1.1/jquery.min.js"></script>
<script
	src="https://cdnjs.cloudflare.com/ajax/libs/sweetalert/1.1.3/sweetalert.min.js"></script>
<style>
</style>
<!-- Table goes in the document BODY -->
<script type="text/javascript">
	function load() {

		//$('#testing').click(function(){

		swal(
				{
					title : "Are you sure? you want to edit this Merchant Details",
					text : "it will be edited..!",
					type : "warning",
					showCancelButton : true,
					confirmButtonText : "Yes, add it!",
					cancelButtonText : "No, cancel!",
					closeOnConfirm : false,
					closeOnCancel : false,
					confirmButtonClass : 'btn btn-success',
					cancelButtonClass : 'btn btn-danger',

				},
				function(isConfirm) {
					if (isConfirm) {

						//swal("Added!", "Your agent details added","success");
						$("#form-edit").submit();

					} else {
						// swal("Cancelled", "Your agent details not added", "error"); 
						var url = "${pageContext.request.contextPath}/admmerchant/list";
						$(location).attr('href', url);
						//return true;
					}
				});
		// });

	}

	function load1() {
		var url = "${pageContext.request.contextPath}/admmerchant/list";
		$(location).attr('href', url);
	}
</script>




<script type="text/javascript">
	/*   function load()
	 {
	 $("#dialog").show();
	 }
	
	
	 function load1()
	
	 {
	 var url = "${pageContext.request.contextPath}/merchant/list/1";
	 $(location).attr('href',url);
	 }
	
	
	 function webDialog()
	
	 {
	 $("#form-edit").submit();
	 }
	
	 function webDialog1()
	 {
	 var url = "${pageContext.request.contextPath}/merchant/list"; 
	 $(location).attr('href',url);
	 }
	 */

	function addrow() {
		//alert("dsfdfdfdf");
		//var i =${merchant.ownerCount};
		//alert("Data :"+i);
		var i = document.getElementById("ownerCount").value;
		//alert(i);
		disableRow();
		//var i=ownercnt.value;
		//	document.getElementById("ownercnt" +i).style.display='';
		//alert(i);
		var a = 2;
		for (a = 2; a <= i; a++) {
			//alert(a);
			document.getElementById("owner" + a).style.display = '';
			document.getElementById("owner" + a + a).style.display = '';
		}
	}
	function disableRow() {
		document.getElementById("owner2").style.display = 'none';
		document.getElementById("owner22").style.display = 'none';
		document.getElementById("owner3").style.display = 'none';
		document.getElementById("owner33").style.display = 'none';
		document.getElementById("owner4").style.display = 'none';
		document.getElementById("owner44").style.display = 'none';
		document.getElementById("owner5").style.display = 'none';
		document.getElementById("owner55").style.display = 'none';
	}
	
	<script>
    document.addEventListener('DOMContentLoaded', function() {
        var maxPayoutLimitDisplay = document.getElementById('maxPayoutLimitDisplay');
        if (!maxPayoutLimitDisplay.value) {
            maxPayoutLimitDisplay.placeholder = 'N/A';
        }
    });
</script>
</head>
<body onload="addrow()">
	<div class="container-fluid">
		<div class="row">



			<div class="col s12">
				<div class="card blue-bg text-white">
					<div class="card-content">
						<div class="d-flex align-items-center">
							<h3 class="text-white">
								<strong>Edit Merchant Details </strong>
							</h3>
						</div>


					</div>
				</div>
			</div>
		</div>




		<form method="post" id="form-edit"
			action="${pageContext.request.contextPath}<%=MerchantWebController.URL_BASE%>/editReviewandConfirm?manualSettlement=${manualSettlement}"
			commandName="merchant">


			<input type="hidden" name="id" value="${merchant.id}" /><input
				type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}">

			<div class="row">
				<div class="col s12">
					<div class="card border-radius">
						<div class="card-content padding-card">

							<div class="d-flex align-items-center">

								<h3 class="card-title userTitle">
									Office Email: ${merchant.officeEmail} <input type="hidden"
										id="officeEmail" name="officeEmail"
										value="${merchant.officeEmail}">
								</h3>
							</div>






							<div class="row">
								<c:if test="${merchant.mid !=null}">
									<div class="input-field col s12 m4 l4">
										<label for="EzyMotoMID">MID</label> <input type="text"
											id="mid" name="mid" readonly="true" path="mid"
											value="${merchant.mid}" placeholder="EMid" />

									</div>
								</c:if>
								<c:if test="${merchant.ezymotomid !=null}">
									<div class="input-field col s12 m4 l4">
										<label for="EzyMotoMID">EzyMoto MID</label> <input type="text"
											id="ezymotomid" name="ezymotomid" readonly="true"
											path="ezymotomid" value="${merchant.ezymotomid}"
											placeholder="EzyMoto Mid" />

									</div>
								</c:if>
								<c:if test="${merchant.ezywaymid !=null}">
									<div class="input-field col s12 m4 l4">
										<label for="EzyWayMID">EzyWay MID</label> <input type="text"
											id="ezywaymid" name="ezywaymid" readonly="true"
											path="ezywaymid" value="${merchant.ezywaymid}"
											placeholder="EzyWay Mid" />
									</div>
								</c:if>
								<c:if test="${merchant.ezyrecmid !=null}">
									<div class="input-field col s12 m4 l4">
										<label for="EzyRecMID">EzyRec MID</label> <input type="text"
											id="ezyrecmid" name="ezyrecmid" readonly="true"
											path="ezyrecmid" value="${merchant.ezyrecmid}"
											placeholder="EzyRec Mid" />

									</div>
								</c:if>
								<c:if test="${merchant.ezypassmid !=null}">
									<div class="input-field col s12 m4 l4">
										<label for="EzyMotoMID">EzyPass MID</label> <input type="text"
											id="ezypassmid" name="ezypassmid" readonly="true"
											path="ezypassmid" value="${merchant.ezypassmid}"
											placeholder="EzyPass Mid" />
									</div>
								</c:if>
							</div>





							<div class="row">
								<c:if test="${merchant.umMid !=null}">
									<div class="input-field col s12 m4 l4">
										<label for="umMid">UM_MID</label> <input type="text"
											id="umMid" name="umMid" readonly="true" path="umMid"
											value="${merchant.umMid}" placeholder="umMid" />
									</div>
								</c:if>
								<c:if test="${merchant.umMotoMid !=null}">
									<div class="input-field col s12 m4 l4">
										<label for="umMotoMid">UM_EzyMoto MID</label> <input
											type="text" id="umMotoMid" name="umMotoMid" readonly="true"
											path="umMotoMid" value="${merchant.umMotoMid}"
											placeholder="umMotoMid" />

									</div>
								</c:if>
								<c:if test="${merchant.umEzywayMid !=null}">
									<div class="input-field col s12 m4 l4">
										<label for="umEzywayMid">UM_EzyWay MID</label> <input
											type="text" id="umEzywayMid" name="umEzywayMid"
											readonly="true" path="umEzywayMid"
											value="${merchant.umEzywayMid}" placeholder="EzyWay Mid" />

									</div>
								</c:if>
								<c:if test="${merchant.ezyrecmid !=null}">
									<div class="input-field col s12 m4 l4">
										<label for="umEzyrecMid">EzyRec MID</label> <input type="text"
											id="umEzyrecMid" name="umEzyrecMid" readonly="true"
											path="umEzyrecMid" value="${merchant.umEzyrecMid}"
											placeholder="umEzyrecMid" />

									</div>
								</c:if>
								<c:if test="${merchant.umEzypassMid !=null}">
									<div class="input-field col s12 m4 l4">
										<label for="umEzypassMid">UM_EzyPass MID</label> <input
											type="text" id="umEzypassMid" name="umEzypassMid"
											readonly="true" path="umEzypassMid"
											value="${merchant.umEzypassMid}" placeholder="umEzypassMid" />

									</div>
								</c:if>
								
								<c:if test="${merchant.fiuuMid !=null}">
									<div class="input-field col s12 m4 l4">
										<label for="fiuuMid">FIUU MID</label> <input
											type="text" id="fiuuMid" name="fiuuMid"
											readonly="true" path="fiuuMid"
											value="${merchant.fiuuMid}" placeholder="fiuuMid" />

									</div>
								</c:if>
							</div>


							<div class="row">
								<div class="input-field col s12 m4 l4">
									<label for="registeredName">Registered Name</label>

									<%-- ${merchant.registeredName} --%>

									<input type="text" id="registeredName"
										placeholder="registeredName" name="registeredName"
										value="${merchant.registeredName}" readonly="readonly">
								</div>

								<div class="input-field col s12 m4 l4">
									<label for="businessName">Business Name</label>
									<%-- ${merchant.businessName} --%>
									<input type="text" id="businessName" placeholder="businessName"
										name="businessName" value="${merchant.businessName}"
										readonly="readonly">
								</div>
								<div class="input-field col s12 m4 l4">
									<label for="businessRegNo">Business Reg No</label>
									<%-- ${merchant.businessRegNo} --%>
									<input type="text" id="businessRegNo"
										placeholder="businessRegNo" name="businessRegNo"
										value="${merchant.businessRegNo}" readonly="readonly">
								</div>

							</div>

							<div class="d-flex align-items-center">
								<h4>Address Details</h4>
							</div>

							<div class="row">
								<div class="input-field col s12 m4 l4">
									<label for="registeredName">Registered Address</label>
									<%-- ${merchant.registeredAddress} --%>
									<%-- <input type="text" class="form-control" id="registeredAddress" placeholder="registeredAddress" name="registeredAddress" value="${merchant.registeredAddress}" readonly="readonly"> --%>

									<textarea readonly="readonly" row="2" column="2"
										max-length="120" class="form-control" id="registeredAddress"
										style="word-break: break-all;" name="registeredAddress"
										readonly="readonly">${merchant.registeredAddress}</textarea>
								</div>

								<div class="input-field col s12 m4 l4">
									<label for="registeredName">Business Address</label>
									<%-- ${merchant.businessAddress} --%>
									<%-- 	<input type="text" class="form-control" id="businessAddress" placeholder="businessAddress" name="businessAddress" value="${merchant.businessAddress}" readonly="readonly"> --%>
									<textarea readonly="readonly" row="2" column="2"
										max-length="120" class="form-control" id="businessAddress"
										style="word-break: break-all;" name="businessAddress"
										readonly="readonly">${merchant.businessAddress}</textarea>
								</div>

								<div class="input-field col s12 m4 l4">
									<label for="registeredName">Mailing Address</label>
									<%-- ${merchant.mailingAddress} --%>
									<%-- <input type="text" class="form-control" id="mailingAddress" placeholder="mailingAddress" name="mailingAddress" value="${merchant.mailingAddress}" readonly="readonly"> --%>
									<textarea readonly="readonly" row="2" column="2"
										max-length="120" class="form-control" id="mailingAddress"
										style="word-break: break-all;" name="mailingAddress"
										readonly="readonly">${merchant.mailingAddress}</textarea>
								</div>

								<div class="input-field col s12 m4 l4">
									<label for="businessCity">Business City</label>
									<%-- ${merchant.businessCity} --%>
									<input type="text" id="txtRegName" placeholder="mailId"
										name="mailId" value="${merchant.businessCity}"
										readonly="readonly">
								</div>

								<div class="input-field col s12 m4 l4">
									<label for="businessPostCode">Business PostCode</label>
									<%-- ${merchant.businessPostCode} --%>
									<input type="text" id="businessPostCode"
										placeholder="businessPostCode" name="businessPostCode"
										value="${merchant.businessPostCode}" readonly="readonly">
								</div>

								<div class="input-field col s12 m4 l4">
									<label for="businessState">Business State</label>
									<%-- ${merchant.businessState} --%>
									<input type="text" id="businessState"
										placeholder="businessState" name="businessState"
										value="${merchant.businessState}" readonly="readonly">
								</div>

							</div>


							<div class="d-flex align-items-center">
								<h4>Contact Person Details</h4>
							</div>

							<div class="row">
								<div class="input-field col s12 m4 l4">
									<label for="salutation">Salutation</label>
									<%-- ${merchant.salutation} --%>
									<input type="text" id="salutation" placeholder="salutation"
										name="salutation" value="${merchant.salutation}"
										readonly="readonly">
								</div>

								<div class="input-field col s12 m4 l4">
									<label for="name">Contact Person Name</label>
									<%-- ${merchant.name} --%>
									<input type="text" id="name" placeholder="name" name="name"
										value="${merchant.name}" readonly="readonly">
								</div>

								<div class="input-field col s12 m4 l4">
									<label for="email">Email</label>
									<%-- ${merchant.email} --%>
									<input type="text" id="email" placeholder="email" name="email"
										value="${merchant.email}" readonly="readonly">
								</div>


								<div class="input-field col s12 m4 l4">
									<label for="email">Secondary email</label>
									<%-- ${merchant.email} --%>
									<input type="text" id="secondary_emaill" placeholder="Secondary email" name="secondary_emaill"
										   value="${merchantInfo.secoundaryEmail}" readonly="readonly">
								</div>



								<div class="input-field col s12 m4 l4">
									<label for="email">Contact No</label>
									<%-- ${merchant.contactNo} --%>
									<input type="text" id="contactNo" placeholder="contactNo"
										name="contactNo" value="${merchant.contactNo}"
										readonly="readonly">
								</div>

							</div>



							<div class="row">
								<div class="input-field col s12 m4 l4">
									<h4>Owner's/Director's Details</h4>

									<%--   <h2 class="boxHeadline">${merchant.ownerCount}</h2> --%>
									<input type="text" path="ownerCount"
										value="${merchant.ownerCount}" id="ownerCount"
										class="form-control" name="ownerCount" readonly="readonly">
								</div>
							</div>
							<div class="row">
								<div class="input-field col s12 m4 l4">
									<label for="ownerSalutation1">Owner Salutation1</label>
									<%-- ${merchant.ownerSalutation1} --%>
									<input type="text" id="ownerSalutation1"
										placeholder="ownerSalutation1" name="ownerSalutation1"
										value="${merchant.ownerSalutation1}" readonly="readonly">
								</div>

								<div class="input-field col s12 m4 l4">
									<label for="email">Owner Name1</label>
									<%-- ${merchant.ownerName1} --%>
									<input type="text" id="ownerName1" placeholder="ownerName1"
										name="ownerName1" value="${merchant.ownerName1}"
										readonly="readonly">
								</div>

								<div class="input-field col s12 m4 l4">
									<label for="email">Owner ContactNo1</label>
									<%-- ${merchant.ownerContactNo1} --%>
									<input type="text" id="ownerContactNo1"
										placeholder="ownerContactNo1" name="ownerContactNo1"
										value="${merchant.ownerContactNo1}" readonly="readonly">
								</div>

							</div>




							<div class="row">
								<div class="input-field col s12 m4 l4">
									<label for="email">NRIC Passport1</label>
									<%-- ${merchant.passportNo1} --%>
									<input type="text" id="passportNo1" placeholder="passportNo1"
										name="passportNo1" value="${merchant.passportNo1}"
										readonly="readonly">
								</div>

								<div class="input-field col s12 m4 l4">
									<label for="residentialAddress1">Residential Address</label>
									<%-- ${merchant.residentialAddress1} --%>
									<input type="text" id="txtRegName" placeholder="mailId"
										name="mailId" value="${subagent.mailId}" readonly="readonly">
								</div>

							</div>





							<!-- owner details2 -->
							<div style="display: none;" id="owner2">
								<div class="row">
									<div class="input-field col s12 m4 l4">
										<label for="ownerSalutation2">Owner Salutation2</label>
										<%-- ${merchant.ownerSalutation2} --%>
										<input type="text" id="ownerSalutation2"
											placeholder="ownerSalutation2" name="ownerSalutation2"
											value="${merchant.ownerSalutation2}" readonly="readonly">
									</div>

									<div class="input-field col s12 m4 l4">
										<label for="ownerName2">Owner Name2</label>
										<%-- ${merchant.ownerName2} --%>
										<input type="text" id="ownerName2" placeholder="ownerName2"
											name="ownerName2" value="${merchant.ownerName2}"
											readonly="readonly">
									</div>

									<div class="input-field col s12 m4 l4">
										<label for="ownerContactNo2">Owner ContactNo2</label>
										<%-- ${merchant.ownerContactNo2} --%>
										<input type="text" id="ownerContactNo2"
											placeholder="ownerContactNo2" name="ownerContactNo2"
											value="${merchant.ownerContactNo2}" readonly="readonly">
									</div>

								</div>

							</div>


							<div style="display: none;" id="owner22">
								<div class="row">
									<div class="input-field col s12 m4 l4">
										<label for="passportNo2">NRIC Passport2</label>
										<%-- ${merchant.passportNo2} --%>
										<input type="text" id="passportNo2" placeholder="passportNo2"
											name="passportNo2" value="${merchant.passportNo2}"
											readonly="readonly">
									</div>

									<div class="input-field col s12 m4 l4">
										<label for="email">Residential Address2</label>
										<%-- ${merchant.residentialAddress2} --%>
										<input type="text" id="residentialAddress2"
											placeholder="residentialAddress2" name="residentialAddress2"
											value="${merchant.residentialAddress2}" readonly="readonly">
									</div>

								</div>
							</div>

							<!-- -owner details3 -->

							<div style="display: none;" id="owner3">
								<div class="row">
									<div class="input-field col s12 m4 l4">
										<label for="email">Owner Salutation3</label>
										<%-- ${merchant.ownerSalutation3} --%>
										<input type="text" id="ownerSalutation3"
											placeholder="ownerSalutation3" name="ownerSalutation3"
											value="${merchant.ownerSalutation3}" readonly="readonly">
									</div>

									<div class="input-field col s12 m4 l4">
										<label for="email">Owner Name3</label>
										<%-- ${merchant.ownerName3} --%>
										<input type="text" id="ownerName3" placeholder="ownerName3"
											name="ownerName3" value="${merchant.ownerName3}"
											readonly="readonly">
									</div>

									<div class="input-field col s12 m4 l4">
										<label for="ownerContactNo3">Owner ContactNo3</label>
										<%-- ${merchant.ownerContactNo3} --%>
										<input type="text" id="ownerContactNo3"
											placeholder="ownerContactNo3" name="ownerContactNo3"
											value="${merchant.ownerContactNo3}" readonly="readonly">
									</div>

								</div>

							</div>


							<div style="display: none;" id="owner33">
								<!-- <div id="owner33"> -->
								<div class="row">
									<div class="input-field col s12 m4 l4">
										<label for="passportNo3">NRIC Passport3</label>
										<%-- ${merchant.passportNo3} --%>
										<input type="text" class="form-control" id="passportNo3"
											placeholder="passportNo3" name="passportNo3"
											value="${merchant.passportNo3}" readonly="readonly">
									</div>

									<div class="input-field col s12 m4 l4">
										<label for="residentialAddress3">Residential Address3</label>
										<%-- ${merchant.residentialAddress3} --%>
										<input type="text" class="form-control"
											id="residentialAddress3" placeholder="residentialAddress3"
											name="residentialAddress3"
											value="${merchant.residentialAddress3}" readonly="readonly">
									</div>

								</div>
							</div>

							<!-- owner Details4 -->
							<div style="display: none;" id="owner4">
								<div class="row">
									<div class="input-field col s12 m4 l4">
										<label for="ownerSalutation4">Owner Salutation4</label>
										<%-- ${merchant.ownerSalutation4} --%>
										<input type="text" class="form-control" id="ownerSalutation4"
											placeholder="ownerSalutation4" name="ownerSalutation4"
											value="${merchant.ownerSalutation4}" readonly="readonly">
									</div>

									<div class="input-field col s12 m4 l4">
										<label for="ownerName4">Owner Name4</label>
										<%-- ${merchant.ownerName4} --%>
										<input type="text" class="form-control" id="ownerName4"
											placeholder="ownerName4" name="ownerName4"
											value="${merchant.ownerName4}" readonly="readonly">
									</div>

									<div class="input-field col s12 m4 l4">
										<label for="ownerContactNo4">Owner ContactNo4</label>
										<%-- ${merchant.ownerContactNo4} --%>
										<input type="text" class="form-control" id="ownerContactNo4"
											placeholder="ownerContactNo4" name="ownerContactNo4"
											value="${merchant.ownerContactNo4}" readonly="readonly">
									</div>

								</div>

							</div>



							<div style="display: none;" id="owner44">
								<div class="row">
									<div class="input-field col s12 m4 l4">
										<label for="email">NRIC Passport4</label>
										<%-- ${merchant.passportNo4} --%>
										<input type="text" class="form-control" id="passportNo4"
											placeholder="passportNo4" name="passportNo4"
											value="${merchant.passportNo4}" readonly="readonly">
									</div>

									<div class="input-field col s12 m4 l4">
										<label for="email">Residential Address4</label>
										<%-- ${merchant.residentialAddress4} --%>
										<input type="text" class="form-control"
											id="residentialAddress4" placeholder="residentialAddress4"
											name="residentialAddress4"
											value="${merchant.residentialAddress4}" readonly="readonly">
									</div>

								</div>
							</div>

							<!--  owner Details5 -->

							<div style="display: none;" id="owner5">
								<div class="row">
									<div class="input-field col s12 m4 l4">
										<label for="email">Owner Salutation5</label>
										<%-- ${merchant.ownerSalutation5} --%>
										<input type="text" class="form-control" id="ownerSalutation5"
											placeholder="ownerSalutation5" name="ownerSalutation5"
											value="${merchant.ownerSalutation5}" readonly="readonly">
									</div>

									<div class="input-field col s12 m4 l4">
										<label for="ownerName5">Owner Name5</label>
										<%-- ${merchant.ownerName5} --%>
										<input type="text" class="form-control" id="ownerName5"
											placeholder="ownerName5" name="ownerName5"
											value="${merchant.ownerName5}" readonly="readonly">
									</div>

									<div class="input-field col s12 m4 l4">
										<label for="ownerContactNo5">Owner ContactNo5</label>
										<%-- ${merchant.ownerContactNo5} --%>
										<input type="text" class="form-control" id="ownerContactNo5"
											placeholder="ownerContactNo5" name="ownerContactNo5"
											value="${merchant.ownerContactNo5}" readonly="readonly">
									</div>

								</div>

							</div>

							<div style="display: none;" id="owner55">

								<div class="row">
									<div class="input-field col s12 m4 l4">
										<label for="passportNo5">NRIC Passport5</label>
										<%-- ${merchant.passportNo5} --%>
										<input type="text" class="form-control" id="passportNo5"
											placeholder="passportNo5" name="passportNo5"
											value="${merchant.passportNo5}" readonly="readonly">
									</div>

									<div class="input-field col s12 m4 l4">
										<label for="residentialAddress5">Residential Address5</label>
										${merchant.residentialAddress5} <input type="text"
											class="form-control" id="residentialAddress5"
											placeholder="residentialAddress5" name="residentialAddress5"
											value="${merchant.residentialAddress5}" readonly="readonly">
									</div>

								</div>
							</div>


							<div class="d-flex align-items-center">
								<h4>Office Details</h4>
							</div>

							<div class="row">
								<div class="input-field col s12 m4 l4">
									<label for="website">Website</label>
									<%-- ${merchant.website} --%>
									<input type="text" id="website" placeholder="website"
										name="website" value="${merchant.website}" readonly="readonly">


								</div>


								<div class="input-field col s12 m4 l4">
									<label for="officeNo">OfficeNo</label>
									<%-- ${merchant.officeNo} --%>
									<input type="text" class="form-control" id="officeNo"
										placeholder="officeNo" name="officeNo"
										value="${merchant.officeNo}" readonly="readonly">
								</div>


								<div class="input-field col s12 m4 l4">
									<label for="faxNo">FaxNo</label>
									<%-- ${merchant.faxNo} --%>
									<input type="text" class="form-control" id="faxNo"
										placeholder="faxNo" name="faxNo" value="${merchant.faxNo}"
										readonly="readonly">
								</div>

							</div>







							<div class="d-flex align-items-center">
								<h4>Business Details & Documents</h4>
							</div>

							<div class="row">
								<div class="input-field col s12 m4 l4">
									<label for="businessType">Business Type</label>
									<%-- ${merchant.businessType} --%>
									<input type="text" class="form-control" id="businessType"
										placeholder="businessType" name="businessType"
										value="${merchant.businessType}" readonly="readonly">
								</div>

								<div class="input-field col s12 m4 l4">
									<label for="companyType">Company Type</label>
									<%-- ${merchant.companyType} --%>
									<input type="text" class="form-control" id="companyType"
										placeholder="companyType" name="companyType"
										value="${merchant.companyType}" readonly="readonly">
								</div>

								<div class="input-field col s12 m4 l4">
									<label for="companyType">NatureOf Business</label>
									<%-- ${merchant.natureOfBusiness} --%>
									<input type="text" class="form-control" id="natureOfBusiness"
										placeholder="natureOfBusiness" name="natureOfBusiness"
										value="${merchant.natureOfBusiness}" readonly="readonly">
								</div>

							</div>


							<div class="d-flex align-items-center">
								<h4>Banks Details & Other Details</h4>
							</div>

							<div class="row">
								<div class="input-field col s12 m4 l4">
									<label for="bankName">Bank Name</label>
									<%-- ${merchant.bankName} --%>
									<input type="text" class="form-control" id="bankName"
										placeholder="bankName" name="bankName"
										value="${merchant.bankName}" readonly="readonly">
								</div>

								<div class="input-field col s12 m4 l4">
									<label for="bankName">Account No</label>
									<%-- ${merchant.bankAccNo} --%>
									<input type="text" class="form-control" id="bankAccNo"
										placeholder="bankAccNo" name="bankAccNo"
										value="${merchant.bankAccNo}" readonly="readonly">
								</div>
							</div>
							<div class="row">
								<div class="input-field col s12 m4 l4">
									<label for="businessRegNo">documents</label>

									<%-- ${merchant.documents} --%>
									<input type="text" class="form-control" id="documents"
										placeholder="documents" name="documents"
										value="${merchant.documents}" readonly="readonly">

								</div>
								<div class="input-field col s12 m4 l4">
									<div>
										<c:if test="${merchant.formFName != null}">
											<input type="text" class="form-control" id="formFName"
												placeholder="formFName" name="formFName"
												value="${merchant.formFName}" readonly="readonly">
											<%--   ${merchant.formFName} --%>
										</c:if>
									</div>
									<div>
										<c:if test="${merchant.docFName != null}">
											<%-- <br>${merchant.docFName} --%>

											<input type="text" class="form-control" id="docFName"
												placeholder="docFName" name="docFName"
												value="${merchant.docFName}" readonly="readonly">
										</c:if>
									</div>
									<div>
										<c:if test="${merchant.payFName != null}">
											<%-- <br>${merchant.payFName} --%>
											<input type="text" class="form-control" id="payFName"
												placeholder="payFName" name="payFName"
												value="${merchant.payFName}" readonly="readonly">
										</c:if>
									</div>


								</div>

							</div>



							<div class="row">
								<div class="input-field col s12 m4 l4">
									<label for="tradingName">Trading Name</label>
									<%-- ${merchant.tradingName} --%>
									<input type="text" class="form-control" id="tradingName"
										placeholder="tradingName" name="tradingName"
										value="${merchant.tradingName}" readonly="readonly">
								</div>

								<div class="input-field col s12 m4 l4">
									<label for="tradingName">Referral Id</label>
									<%-- ${merchant.referralId} --%>
									<input type="text" class="form-control" id="referralId"
										placeholder="referralId" name="referralId"
										value="${merchant.referralId}" readonly="readonly">
								</div>

								<div class="input-field col s12 m4 l4">
									<label for="tradingName">No.Of Reader</label>
									<%-- ${merchant.noOfReaders} --%>
									<input type="text" class="form-control" id="noOfReaders"
										placeholder="noOfReaders" name="noOfReaders"
										value="${merchant.noOfReaders}" readonly="readonly">
								</div>


								<div class="input-field col s12 m4 l4">
									<label for="tradingName">Signed Package</label>
									<%-- ${merchant.signedPackage} --%>
									<input type="text" class="form-control" id="signedPackage"
										placeholder="signedPackage" name="signedPackage"
										value="${merchant.signedPackage}" readonly="readonly">
								</div>

								<div class="input-field col s12 m4 l4">
									<label for="tradingName">WavierMonth</label>
									<%-- ${merchant.wavierMonth} --%>
									<input type="text" class="form-control" id="wavierMonth"
										placeholder="wavierMonth" name="wavierMonth"
										value="${merchant.wavierMonth}" readonly="readonly">
								</div>

								<div class="input-field col s12 m4 l4">
									<label for="tradingName">Remarks</label>
									<%-- ${merchant.statusRemarks} --%>
									<input type="text" class="form-control" id="statusRemarks"
										placeholder="statusRemarks" name="statusRemarks"
										value="${merchant.statusRemarks}" readonly="readonly">
								</div>



								<div class="input-field col s12 m4 l4">
									<label for="yearIncorporated">Year Incorporated</label>
									<%-- ${merchant.yearIncorporated} --%>
									<input type="text" class="form-control" id="yearIncorporated"
										placeholder="yearIncorporated" name="yearIncorporated"
										value="${merchant.yearIncorporated}" readonly="readonly">
								</div>

								<div class="input-field col s12 m4 l4">
									<label for="agentName">Agent Name</label>
									<%-- ${merchant.agentName} --%>
									<input type="text" class="form-control" id="agentName"
										placeholder="agentName" name="agentName"
										value="${merchant.agentName}" readonly="readonly">
								</div>

								<div class="input-field col s12 m4 l4">
									<label for="mdr">Mdr</label>
									<%-- ${merchant.mdr} --%>
									<input type="text" class="form-control" id="mdr"
										placeholder="mdr" name="mdr" value="${merchant.mdr}"
										readonly="readonly">
								</div>

								<div class="input-field col s12 m4 l4">
									<label for="accType">Account Type</label>
									<%-- ${merchant.mdr} --%>
									<input type="text" class="form-control" id="accType"
										placeholder="accType" name="accType"
										value="${merchant.accType}" readonly="readonly">
								</div>

							</div>


							<div class="row">

								<div class="input-field col s12 m4 l4">
									<label for="preAuth">Pre-Auth</label>
									<%-- ${merchant.preAuth} --%>
									<%-- <span id="preAuth"  name="preAuth"></span><span>${subagent.preAuth}</span> --%>
									<input type="text" class="form-control" id="preAuth"
										placeholder="preAuth" name="preAuth"
										value="${merchant.preAuth}" readonly="readonly">
								</div>

								<div class="input-field col s12 m4 l4">
									<label for="autoSettled">Auto Settled</label> <input
										type="text" class="form-control" id="autoSettled"
										placeholder="autoSettled" name="autoSettled"
										value="${merchant.autoSettled}" readonly="readonly">
								</div>

								<div class="input-field col s12 m4 l4">
									<label for="auth3DS">3DS</label> <input type="text"
										class="form-control" id="auth3DS" placeholder="auth3DS"
										name="auth3DS" value="${merchant.auth3DS}" readonly="readonly">
								</div>


								<div class="input-field col s12 m4 l4">
									<label for="manualSettlement">Payout Settlement</label> <input
										type="text" class="form-control" id="manualSettlement"
										placeholder="payoutSettlement" name="auth3DS"
										value="${manualSettlement}" readonly="readonly">
								</div>

								<div class="input-field col s12 m4 l4">
									<label for="merchantStatus">Merchant Status</label> <input
										type="text" class="form-control" id="merchantStatus"
										placeholder="merchantStatus" name="merchantStatus"
										value="${merchant.status}" readonly="readonly">
								</div>

								<div class="input-field col s12 m4 l4">
									<label for="merchantStatus">Payout Async Handler</label> <input
										type="text" class="form-control" id=""
										placeholder="Payout Async Handler" name=""
										value="${merchant.isAsyncPayoutEnabled}" readonly="readonly">
								</div>

								<div class="input-field col s12 m4 l4">
                                    <label for="accountEnquiryEnabled">Account Enquiry</label> <input
                                        type="text" class="form-control" id=""
                                        placeholder="Account Enquiry" name="accountEnquiryEnabled"
                                        value="${merchant.isAccountEnquiryEnabled}" readonly="readonly">
                                </div>
                                <div class="input-field col s12 m4 l4">
                                    <label for="quickPayoutEnabled">Quick Payout</label> <input
                                        type="text" class="form-control" id=""
                                        placeholder="Quick Payout" name="quickPayoutEnabled"
                                        value="${merchant.isQuickPayoutEnabled}" readonly="readonly">
                                    </div>

                               <!--
                                <div class="input-field col s12 m4 l4">
                                    <label for="quickPayoutUrl">Quick Payout URL</label>
                                    <input
                                        type="text" class="form-control" id=""
                                        placeholder="" name="quickPayoutUrl"
                                        value="${merchant.quickPayoutUrl}" readonly="readonly">
                                </div>
                                -->


								<c:if test="${merchant.isAsyncPayoutEnabled == 'Yes'}">
									<div class="input-field col s12 m4 l4">
										<label for="merchantStatus">Payout Notification URL</label> <input
											type="text" class="form-control" id=""
											placeholder="Payout Notification URL" name=""
											value="${merchant.payoutIpnUrl}" readonly="readonly">
									</div>
									<div class="input-field col s12 m4 l4">
										<label for="merchantStatus">Reason for Enabling Async
											Payout</label> <input type="text" class="form-control" id=""
											placeholder="Reason for Enabling Async Payout" name=""
											value="${merchant.asyncEnableReason}" readonly="readonly">
									</div>
								</c:if>
								
								<div class="input-field col s12 m4 l4">
								    <label for="merchantStatus">Max Payout Limit Set</label>
								    <input type="text" class="form-control" id="maxPayoutLimitDisplay"
								           placeholder="N/A" name="maxPayoutLimitDisplay"
								           value="${merchant.maxPayoutTxnLimit}" readonly="readonly">
								</div>
								
<%-- 								<div class="input-field col s12 m4 l4">
									<label for="merchantStatus">Max Payout Limit Set</label> <input
										type="text" class="form-control" id=""
										placeholder="" name=""
										value="${merchant.maxPayoutTxnLimit}" readonly="readonly">
								</div>	 --%>							

								<c:choose>
									<c:when test="${adminusername.toLowerCase()=='ethan'}">

										<div class="input-field col s12 m4 l4">
											<label for="foreignCard">Enable ForeignCard</label> <input
												type="text" class="form-control" id="foreignCard"
												placeholder="foreignCard" name="foreignCard"
												value="${merchant.foreignCard}" readonly="readonly">
										</div>

									</c:when>
								</c:choose>

								<c:choose>
									<c:when test="${adminusername.toLowerCase()=='ethan'}">
										<div class="input-field col s12 m4 l4">
											<label for="ezysettle">Enable Ezysettle</label> <input
												type="text" class="form-control" id="ezysettle"
												placeholder="ezysettle" name="ezysettle"
												value="${merchant.ezysettle}" readonly="readonly">
										</div>
									</c:when>
								</c:choose>

							</div>


							<div class="row">

								<div class="input-field col s12 m4 l4">
									<div class="button-class" style="float: left;">
										<button type="button" id="testing" onclick="return load()"
											class="btn btn-primary blue-btn">Confirm</button>

										<button type="button" onclick=" return load1()"
											class="btn btn-primary blue-btn">Cancel</button>

									</div>
								</div>
							</div>

							<style>
.select-wrapper .caret {
	fill: #005baa;
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

						</div>
					</div>
				</div>
			</div>

		</form>
	</div>


</body>
</html>
