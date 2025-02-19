<%@page import="com.mobiversa.payment.controller.MobileUserController"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<!-- <html lang="en-US">
<head> -->
<meta charset="UTF-8">
<meta
	content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no"
	name="viewport">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
 <script type="text/javascript" src="https://ajax.googleapis.com/ajax/libs/jquery/3.1.0/jquery.min.js"></script>
  
 <script type="text/javascript" src="${pageContext.request.contextPath}/resourcesNew/js/plugins/select2.min.js"></script>
 <script type="text/javascript" src="${pageContext.request.contextPath}/resourcesNew/js/urlHide.js"></script>
 
 <style>
.error {
	color: red;
	font-weight: bold;
}


</style>
</head>


<script type="text/javascript">
jQuery(document).ready(function() {
$('#merchantName').select2();
$('#referenceNo').select2();
$('#motoreferenceNo').select2();
$('#ezypassreferenceNo').select2();

$('#connectType').select2();
$('#deviceType').select2();
//$('#renewalPeriod').select2();
});  
    </script>



 <script lang="JavaScript">
 
 
 
 function disableRow1() {

		//alert("test123");

		var i = document.getElementById("connectType").value;
		//alert(i);

		if (i == "WIFI") {
			//document.getElementById("agType").value;

			document.getElementById("preAuth1").style.display = 'none';

			//document.getElementById("BankDetails1").style.display = 'none';

		} else if (i == "BT") {

			document.getElementById("preAuth1").style.display = '';

			//document.getElementById("BankDetails1").style.display = '';
		}

	}
 

       function checkBox()
       {
	
	var preAuth = document.getElementById("preAuth").value;
	
	alert("check preAuth:" + preAuth);
	
       }



      function loadSelectData()
       { 
       
       
       var e = document.getElementById("merchantName").value;
       
      /*  if (e == null || e == '' )
        {
       
			alert("Please Select BusinessName");
			
			return false;
			} */
       
       
       var e1 = document.getElementById("connectType").value;
       
       if (e1 == null || e1 == '' ) {
       
			alert("Please Select connectType");
			 //form.submit = false; 
			return false;
			}  
       
       if(!allnumeric(document.form1.tid, 8, 8))
		  {
		   return false;
		  } 
     
       
 		var e2 = document.getElementById("deviceType").value;
       
       if (e2 == null || e2 == '' ) {
       
			alert("Please Select deviceType");
			 //form.submit = false; 
			return false;
			}  
			
		if(!allnumeric(document.form1.deviceId, 12, 14))
		  {
		   return false;
		  } 
		  
		  var e = document.getElementById("referenceNo").value;
	       
	       if (e == null || e == '' ) {
	       
				alert("Please Select Reference No");
				 //form.submit = false; 
				return false;
				}    
		    
		  var e = document.getElementById("renewalPeriod").value;
	       
	       if (e == null || e == '' ) {
	       
				alert("Please Select Renewal Period");
				 //form.submit = false; 
				return false;
				}    
		    
		  
		  if(!alphanumeric(document.form1.deviceId, 6, 16))
		  
		  {
		  return false;
		  } 
		  
		  if(document.form1.contactName.value != ''){
		  	  if(!allLetterSpace(document.form1.contactName, 2, 100))
				  
			  {
			  return false;
			  } 
		  }
       }  
      // }    
      
        
        //allnumeric tid & reference No validation
        function allnumeric(inputtxt, minlength, maxlength) {
		var field = inputtxt.value;

		var mnlen = minlength;
		var mxlen = maxlength;
		//var uzip = document.registration.zip;  
		var numbers = /^[0-9]+$/;
		if ((field.length < mnlen) || (field.length > mxlen)) {
			alert("Please input the " + inputtxt.name + " between " + mnlen
					+ " and " + mxlen + " characters");
			return false;
		} else if (field.match(numbers)) {
			// Focus goes to next field i.e. email.  
			//document.registration.email.focus();  
			return true;
		} else {
			alert(inputtxt.name + ' must have numeric characters only');
			//uzip.focus();  
			return false;
		}
	}
       
       //alphanumeric deviceID validation
       
       function alphanumeric(inputtxt, minlength, maxlength) {
		var field = inputtxt.value;
		var mnlen = minlength;
		var mxlen = maxlength;
		// var uadd = document.registration.address;  
		var letters = /^[0-9a-zA-Z]+$/;
		if ((field.length < mnlen) || (field.length > mxlen)) {
			alert("Please input the " + inputtxt.name + " between " + mnlen
					+ " and " + mxlen + " characters");
			return false;
		} else if (field.match(letters)) {
			// Focus goes to next field i.e. Country.  
			//document.registration.country.focus();  
			return true;
		} else {
			alert(inputtxt.name + ' must have alphanumeric characters only');
			// uadd.focus();  
			return false;
		}
	}
	
	
	
	function allLetterSpace(inputtxt, minlength, maxlength) {

		var field = inputtxt.value;
		var mnlen = minlength;
		var mxlen = maxlength;
		// var uname = document.registration.username;  
		var letters =  /^[a-zA-Z0-9 ]*$/;/*  /^[A-Za-z]+$/; */
		if ((field.length == 0) || (field.length < mnlen)
				|| (field.length > mxlen)) {
			alert("Please input the " + inputtxt.name + " between " + mnlen
					+ " and " + mxlen + " characters");
			return false;
		} else if (field.match(letters)) {
			// Focus goes to next field i.e. Address.  
			//document.form1.address.focus();  
			return true;
		} else {
			alert(inputtxt.name + ' must have alphanumeric and space only');
			//uname.focus();  
			return false;
		}
	}
	
	
	
	function validateEmail(inputtxt) {
		//alert(inputtxt);
		var field = inputtxt.value;
		//var uemail = document.registration.email;  
		var mailformat = /^\w+([\.-]?\w+)*@\w+([\.-]?\w+)*(\.\w{2,3})+$/;
		if (field.match(mailformat)) {
			//document.registration.desc.focus();  
			return true;
			
		} else if (field.length==0)
		 {
			alert("You have entered  "+inputtxt.name+" address!");
			//uemail.focus();  
			return false;
		} else {
		alert("You have entered an invalid "+inputtxt.name+" address!");
		}
	}
	
	
	
	 function checkRenewal()
       {
       
       var monthNames = ["Jan","Feb","Mar","Apr","May","Jun","Jul","Aug","Sep","Oct","Nov","Dec"];
	
		var renewal = document.getElementById("renewalPeriod").value;
	
		//alert("check renewal:" + renewal);
	
		/* var myDate = new Date();
		myDate.setDate(myDate.getDate() + renewal); */
		
		
		
		var CurrentDate = new Date();
		/* alert("check 2222222222222 :" + CurrentDate.getFullYear());
		var d = new Date(CurrentDate.getFullYear(), CurrentDate.getMonth(), CurrentDate.getDate());
		d.setMonth(d.getMonth() + parseInt(renewal)); */
		
		/* var year = CurrentDate.getFullYear(); */
		//CurrentDate.setMonth(CurrentDate.getMonth() + renewal);
		/* alert("check renewal1111111111111111111111 :" + d);
		alert("check renewal1:" + CurrentDate.getMonth()); */
		CurrentDate.setMonth(CurrentDate.getMonth() + parseInt(renewal));
		//alert("check renewal1123 :" + CurrentDate.getMonth());
		//CurrentDate.setFullYear(CurrentDate.getFullYear());
		
		//alert("check renewal2:" + CurrentDate);
		
		var day = CurrentDate.getDate();
  		var monthIndex = monthNames[CurrentDate.getMonth()];
  		var year = CurrentDate.getFullYear();
		
		document.getElementById("expiryDate").value=day+"-"+monthIndex+"-"+year; 
		//.setMonth(CurrentDate.getMonth() + renewal);
		
		
		//alert("check renewal3:" + document.getElementById("expiryDate").value);
	
       }
	
	
       </script>
       
       <script>
    $(function(){
      // bind change event to select
      $('#merchantName').on('change', function () {
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
       
<body>
	
	
			
			
			<div class="content-wrapper">
			<div class="row">

				<div class="col-md-12 formContianer">
					

					<div class="card">
					
					<h3 class="card-title">Merchant Details</h3>
					
						<div class="row">
							<div class="form-group col-md-4">
								<div class="form-group">
									<label for="Email">Business Name</label>
									</div>
									</div>
									<div class="row">
							<div class="form-group col-md-4">
								<div class="form-group">
									<select class="form-control" name="merchantName"
								id="merchantName" path="merchantName">
								<optgroup label="Business Names" style="width:100%">
								<option selected value=""><c:out value="business Name" /></option>
								<%-- <c:forEach items="${merchantNameList}" var="merchantName">
									<option value="${merchantName}">${merchantName}</option>


								</c:forEach> --%>
								<c:forEach items="${merchant1}" var="merchant1">
									<option value="${pageContext.request.contextPath}/mobileUser/finduserDetails?id=${merchant1.id}">
									
									
									<%-- <c:out value="${merchant1.mid.mid}~${merchant1.businessName}~${merchant1.role}"> --%>
									${merchant1.businessName}~${merchant1.email}~${merchant1.role}<%-- </c:out> --%>
									</option>

								</c:forEach>
								</optgroup>
							</select>
							</div>
							</div>
							
						</div>	</div></div>
				<form method="GET" id="form1" action="${pageContext.request.contextPath}/mobileUser/regMobileUserDetails"
			name="form1" commandName="mobileUser">		
						<div class="card">
					
					<h3 class="card-title">MobileUser Details</h3>
					
						
							<div class="row">
						<div class="form-group col-md-4">
								<div class="form-group">
									<label for="Email">Contact Name</label>
						
							<input type="text" class="form-control" path="contactName"
								name="contactName" Placeholder="contactName"/>

						</div>
					</div>
					<div class="form-group col-md-4">
						<div class="form-group">
							<label for="Renewal Period">Renewal Period(In Months)</label>
							<input type="text" class="form-control" name="renewalPeriod" id="renewalPeriod" 
							value="12" onblur="checkRenewal()" path="renewalPeriod"/>
				<%-- <form:select class="form-control"  name="renewalPeriod" path="renewalPeriod" id="renewalPeriod" style="width:100%" 
									onchange="checkRenewal()">
					<option value="">- Renewal Period -</option>
					<option value=1>1 Month</option>
                    <!-- <option value="61">2 Months</option> -->
                    <!-- <option value=3>3 Months</option>
                    <option value=6>6 Months</option> -->
                    <option value=12>1 Year</option>
                    <option value=24>2 Years</option>
 				</form:select>	 --%>
					</div>
				</div>
						
						<div class="form-group col-md-4">
			<div class="form-group">
				<label for="Expiry Date">Expiry Date</label>
				<input type="text" class="form-control" id="expiryDate" path="expiryDate"
				placeholder="expiryDate" name="expiryDate" />
			</div>
		</div>
						
				<div class="form-group col-md-4">
			<div class="form-group">
				<label for="Remarks">Remarks</label>
				<input type="text" class="form-control" id="remarks" path="remarks"
				placeholder="remarks" name="remarks" />
			</div>
		</div>		
							
								</div></div>
					
					<input type="hidden" value="${midDetails.mid.mid }" name="mid" path="mid"/>
					<div class="card">
						<h3 class="card-title">MobileUser Ezywire Details 
						<c:if test="${midDetails.mid.mid !=null }"> <font color="blue">Mid: ${midDetails.mid.mid }</font></c:if></h3>

						<div class="row">

							<div class="form-group col-md-4">
								<div class="form-group">
									<label for="tid">TID</label>
									<input type="text" class="form-control" id="tid"
										placeholder="tid" name="tid" path="tid" />
									<div>
										<c:if test="${responseData1 != null}">
											<H4 style="color: #ff4000;" align="center">${responseData1}</H4>
										</c:if>
									</div>
								 </div>
							</div>
							<div class="form-group col-md-4">
										<div class="form-group">
											<label for="Email">Device Id</label>
											<input type="text" class="form-control" id="deviceId"
												placeholder="deviceId" name="deviceId" path="deviceId"/>
											<div>
												<c:if test="${responseData2!= null}">
													<H4 style="color: #ff4000;" align="center">${responseData2}</H4>
												</c:if>
											</div>
										</div>
							</div>
							
							<div class="form-group col-md-4">
								<div class="form-group">
									<label for="Email">Reference No</label>
									<select class="form-control" name="referenceNo" path="referenceNo"
										id="referenceNo"  style="width:100%">
										<option selected value=""><c:out value="refNo" /></option>
										<c:forEach items="${refNoList}" var="refNo">
											<option value="${refNo}">${refNo}</option>


										</c:forEach>
									</select>
								</div>
							</div>
							
							<div class="form-group col-md-4">
									<div class="form-group">
										<label for="Pre_Auth">Pre-Auth</label>
										
										<div class="radiobuttons">
											<label> <input type="radio" name="preAuth" path="preAuth"
													 value="Yes" id="preAuth" /> <span>Yes</span>
											</label> <label> <input type="radio" checked="checked"
													name="preAuth" value="No"  id="preAuth"  path="preAuth"/> <span>No</span>
											</label>
										</div>
									</div>
								</div>
							
						</div>
					</div>


					<c:if test="${midDetails.mid.motoMid !=null }">
					<input type="hidden" value="${midDetails.mid.motoMid}" name="motoMid" path="motoMid"/>
					<div class="card">
						<h3 class="card-title">MobileUser Moto Details <font color="blue"> Mid: ${midDetails.mid.motoMid}</font> </h3>

						<div class="row">

							<div class="form-group col-md-4">
								<div class="form-group">
									<label for="tid">TID</label>
									<input type="text" class="form-control" id="motoTid"
										placeholder="motoTid" name="motoTid"  path="motoTid"/>
									<div>
										<c:if test="${responseData1 != null}">
											<H4 style="color: #ff4000;" align="center">${responseData1}</H4>
										</c:if>
									</div>
								 </div>
							</div>
							<div class="form-group col-md-4">
										<div class="form-group">
											<label for="Email">Device Id</label>
											<input type="text" class="form-control" id="motodeviceId"
												placeholder="motodeviceId" name="motodeviceId"  path="motodeviceId"/>
											<div>
												<c:if test="${responseData2!= null}">
													<H4 style="color: #ff4000;" align="center">${responseData2}</H4>
												</c:if>
											</div>
										</div>
							</div>
							
							<div class="form-group col-md-4">
								<div class="form-group">
									<label for="Email">Reference No</label>
									<select class="form-control" name="motorefNo" path="motorefNo"
										id="motorefNo"  style="width:100%">
										<option selected value=""><c:out value="refNo" /></option>
										<c:forEach items="${refNoList}" var="refNo">
											<option value="${refNo}">${refNo}</option>


										</c:forEach>
									</select>
								</div>
							</div>
							
							
							
						</div>
					</div>
					
					
					
					</c:if>


			<c:if test="${midDetails.mid.ezypassMid !=null }">
					<input type="hidden" value="${midDetails.mid.ezypassMid}" name="ezypassMid" path="ezypassMid"/>
					<div class="card">
						<h3 class="card-title">MobileUser EzyPass Details <font color="blue">
						Mid:${midDetails.mid.ezypassMid}</font></h3>

						<div class="row">

							<div class="form-group col-md-4">
								<div class="form-group">
									<label for="tid">TID</label>
									<input type="text" class="form-control" id="ezypassTid"
										placeholder="ezypassTid" name="ezypassTid"  path="ezypassTid"/>
									<div>
										<c:if test="${responseData1 != null}">
											<H4 style="color: #ff4000;" align="center">${responseData1}</H4>
										</c:if>
									</div>
								 </div>
							</div>
							<div class="form-group col-md-4">
										<div class="form-group">
											<label for="Email">Device Id</label>
											<input type="text" class="form-control" id="ezypassdeviceId"
												placeholder="ezypassdeviceId" name="ezypassdeviceId"  path="ezypassdeviceId"/>
											<div>
												<c:if test="${responseData2!= null}">
													<H4 style="color: #ff4000;" align="center">${responseData2}</H4>
												</c:if>
											</div>
										</div>
							</div>
							
							<div class="form-group col-md-4">
								<div class="form-group">
									<label for="Email">Reference No</label>
									<select class="form-control" name="ezypassrefNo" path="ezypassrefNo"
										id="ezypassrefNo" style="width:100%">
										<option selected value=""><c:out value="refNo" /></option>
										<c:forEach items="${refNoList}" var="refNo">
											<option value="${refNo}">${refNo}</option>


										</c:forEach>
									</select>
								</div>
							</div>
							
							
							
						</div>
					</div>
					
					
					
					</c:if>


						<button class="submitBtn" type="submit"
							onclick="return loadSelectData()">Submit</button>
	</form>
					</div>
				</div>
			</div>
		


	</body>
	</html>
	
