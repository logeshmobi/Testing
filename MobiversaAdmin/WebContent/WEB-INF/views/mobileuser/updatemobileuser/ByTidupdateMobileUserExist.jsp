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
 
 <style>
.error {
	color: red;
	font-weight: bold;
}


</style>
</head>

<!-- 
<script type="text/javascript">
jQuery(document).ready(function() {
$('#merchantName').select2();
$('#referenceNo').select2();
$('#motorefNo').select2();
$('#ezypassrefNo').select2();
$('#ezyrecrefNo').select2();
$('#um_refNo').select2();

$('#connectType').select2();
$('#deviceType').select2();
$('#tid1').select2();
$('#tid2').select2();
$('#tid3').select2();
/* $('#motoTid').select2(); */
//$('#renewalPeriod').select2();
});  
    </script> -->
    
  <script type="text/javascript">
jQuery(document).ready(function() {
$('#motoTid').click(function(){
  $('#motoTidErr').hide();
});
}); 
 </script>
 <script type="text/javascript">
jQuery(document).ready(function() {
$('#motodeviceId').click(function(){
  $('#motodeviceIdErr').hide();
});
});
 </script>
 <script type="text/javascript">
jQuery(document).ready(function() {
$('#ezypassTid').click(function(){
  $('#ezypassTidErr').hide();
});
});

 </script>
 <script type="text/javascript">
jQuery(document).ready(function() {
$('#ezypassdeviceId').click(function(){
  $('#ezypassdeviceIdErr').hide();
});
});
 </script>   
    
    
<script type="text/javascript">

jQuery(document).ready(function() {
$('#motorefNo').change(function(){
  if($(this).val() == $('#ezypassrefNo').val()) {
    alert('Duplicate value');
    return false;
   /*  $(this).val(''); */
  }
}); 
}); 

jQuery(document).ready(function() {
$('#ezypassrefNo').change(function(){
  if($(this).val() == $('#motorefNo').val()) {
    alert('Duplicate value');
    return false;
   /*  $(this).val(''); */
  }
}); 
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
    		
    	  var renewalPeriod = document.getElementById("renewalPeriod").value;
	       
	       if (renewalPeriod == null || renewalPeriod == '' ) {
	       
				alert("Please Select Renewal Period");
				 //form.submit = false; 
				return false;
				}    
		    
	       
		var expiryDate = document.getElementById("expiryDate").value;
	       
	       if (expiryDate === null || expiryDate === '' ) {
	       
				alert("Please fill Expiry Date");
				 //form.submit = false; 
				 document.getElementById("expiryDate").focus();
				return false;
				}
			
			//e-moto

			var motoTid = document.getElementById("motoTid").value;
	    	var motodeviceId = document.getElementById("motodeviceId").value;
	    	var motorefNo = document.getElementById("motorefNo").value;
	    	
			if (motoTid != '' && motoTid != null) {
				if (!allnumeric(document.form1.motoTid, 8, 8)) {
					document.getElementById("motoTid").focus();
					return false;
				}
				if (motodeviceId != '' && motodeviceId != null) {
					if (!alphanumeric(document.form1.motodeviceId, 15, 16)) {
						document.getElementById("motodeviceId").focus();
						return false;
					}
				} else {
					alert("Please Fill Moto DeviceId");
					document.getElementById("motodeviceId").focus();
					return false;
				}
				if (motorefNo == null || motorefNo == ''
						|| motorefNo == 'refNo') {

					alert("Please Select Moto Reference No");
					document.getElementById("motorefNo").focus();
					//form.submit = false; 
					return false;
				}

				
			}

			//ezypass
			
			var ezypassTid = document.getElementById("ezypassTid").value;
	    	var ezypassdeviceId = document.getElementById("ezypassdeviceId").value;
	    	var ezypassrefNo = document.getElementById("ezypassrefNo").value;
	    	
			if (ezypassTid != '' && ezypassTid != null) {
				if (!allnumeric(document.form1.ezypassTid, 8, 8)) {
					document.getElementById("ezypassTid").focus();
					return false;
				}

				if (ezypassdeviceId != '' && ezypassdeviceId != null) {
					if (!alphanumeric(document.form1.ezypassdeviceId, 15, 16)) {
						document.getElementById("ezypassdeviceId").focus();
						return false;
					}
				} else {
					alert("Please Fill Ezypass DeviceId");
					document.getElementById("ezypassdeviceId").focus();
					return false;
				}
				if (ezypassrefNo == null || ezypassrefNo == ''
						|| ezypassrefNo == 'refNo') {

					alert("Please Select Ezypass Reference No");
					document.getElementById("ezypassrefNo").focus();
					//form.submit = false; 
					return false;
				}

				

			}

			//ezyrec

			var ezyrecTid = document.getElementById("ezyrecTid").value;
			var ezyrecrefNo = document.getElementById("ezyrecrefNo").value;
			var ezyrecdeviceId = document.getElementById("ezyrecdeviceId").value;
			
			if (ezyrecTid != '' && ezyrecTid != null) {
				if (!allnumeric(document.form1.ezyrecTid, 8, 8)) {
					document.getElementById("ezyrecTid").focus();
					return false;
				}
				if (ezyrecdeviceId != '' && ezyrecdeviceId != null) {
					if (!alphanumeric(document.form1.ezyrecdeviceId, 15, 16)) {
						document.getElementById("ezyrecdeviceId").focus();
						return false;
					}
				} else {
					alert("Please Fill Ezyrec DeviceId");
					document.getElementById("ezyrecdeviceId").focus();
					return false;
				}
				if (ezyrecrefNo == null || ezyrecrefNo == ''
						|| ezyrecrefNo == 'refNo') {

					alert("Please Select Ezyrec Reference No");
					document.getElementById("ezyrecrefNo").focus();
					//form.submit = false; 
					return false;
				}

				
			}

			

			if (motorefNo === '' && ezypassrefNo === '' && ezyrecrefNo === '') {
				alert("Please Fill Anyone Device Details..");
				return false;
			}
			if ((motorefNo === ezypassrefNo) && motorefNo !='' && ezypassrefNo !='') {
				alert("Moto RefNo and Ezypass RefNo are Same.. Please change it as unique ");
				return false;
			}
			if ((ezypassrefNo === ezyrecrefNo) && ezypassrefNo !='' && ezyrecrefNo !='refNo') {
				alert("Ezyrec RefNo and Ezypass RefNo are Same.. Please change it as unique ");
				return false;
			}
			if (ezyrecrefNo === motorefNo && ezyrecrefNo !='' && motorefNo !='') {
				alert("Moto RefNo and Ezyrec RefNo are Same.. Please change it as unique ");
				return false;
			}

			
			var e = document.getElementById("businessName").value;

			if (e == null || e == '') {

				alert("Please Select BusinessName");

				return false;
			}

			

			if (document.form1.contactName.value != '') {
				if (!allLetterSpace(document.form1.contactName, 2, 100))

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
        <script>
    $(function(){
      // bind change event to select
      $('#tid1').on('change', function () {
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
	<div class="container-fluid">    
  <div class="row">
  <div class="col s12">
      <div class="card blue-bg text-white">
        <div class="card-content" style="padding:0px;">
          <div class="d-flex align-items-center">
           <h3 class="text-white">  <strong> Data Exist </strong> </h3>
          </div>           
          
        </div>
      </div>
    </div>
    </div>
	
<div class="row">
	<div class="card border-radius">
        <div class="card-content padding-card">
        	<div class="row">
							<div class="input-field col s12 m3 l3">
								
									<label for="name">Business Name</label>
									
							</div>	
					<div class="row">
							<div class="input-field col s12 m3 l3">
									<select  name="merchantName"
								id="merchantName" path="merchantName">
								<optgroup label="Business Names" style="width:100%">
								<option selected value=""><c:out value="business Name" /></option>
								<%-- <c:forEach items="${merchantNameList}" var="merchantName">
									<option value="${merchantName}">${merchantName}</option>


								</c:forEach> --%>
								<c:forEach items="${merchant1}" var="merchant1">
								<c:if test="${merchant1.role == 'BANK_MERCHANT'}">
									<option value="${pageContext.request.contextPath}/mobileUser/findMobileuserDetails?id=${merchant1.id}">
									
									
									<%-- <c:out value="${merchant1.mid.mid}~${merchant1.businessName}~${merchant1.role}"> --%>
									
									${merchant1.businessName}~${merchant1.username}~${merchant1.role}<%-- </c:out> --%>
									</option>
</c:if>
								</c:forEach>
								</optgroup>
							</select>
							</div>
							</div>
							
						</div>	</div></div></div>
						
				<form method="GET" id="form1" action="${pageContext.request.contextPath}/mobileUser/updateMobileUserDetails"
			name="form1" commandName="mobileUser">		
						<div class="card">
					<input type="hidden" name="updateType" value="ezywire" path="updateType"/>
					<div class="row">
  <div class="col s12">
      <div class="card border-radius">
        <div class="card-content padding-card">
		 <div class="d-flex align-items-center">
					<h3>MobileUser Details</h3></div>					
					</div>
					
						
							<div class="row">
						<div class="input-field col s12 m6 l6 ">
									<label for="Email">Contact Name</label>
						
							<input type="text"  path="contactName"
								name="contactName" Placeholder="contactName" value="${mobileUser.contactName }"/>

						</div>
					
					<div class="input-field col s12 m6 l6 ">
							<label for="Renewal Period">Renewal Period(In Months)</label>
							<input type="text" name="renewalPeriod" id="renewalPeriod" 
							value="12" onblur="checkRenewal()" path="renewalPeriod" value="${mobileUser.renewalPeriod }"/>
				
					</div>
				<div class="input-field col s12 m6 l6 ">
				<label for="Expiry Date">Expiry Date</label>
				<input type="text" id="expiryDate" path="expiryDate"
				placeholder="expiryDate" name="expiryDate" value="${mobileUser.expiryDate }"/>
			</div>
		<div class="input-field col s12 m6 l6 ">
				<label for="Remarks">Remarks</label>
				<input type="text" id="remarks" path="remarks"
				placeholder="remarks" name="remarks"  value="${mobileUser.remarks }"/>
			</div>
		</div>		
							
								</div></div></div></div>
								
							<c:if test="${mobileUser.mid !=null}">
					<input type="hidden" value="${mobileUser.mid }" name="mid" path="mid"/>
					<div class="row">
  <div class="col s12">
      <div class="card border-radius">
        <div class="card-content padding-card">
		 <div class="d-flex align-items-center">
					
						<h3 >MobileUser Ezywire Details 
						<c:if test="${mobileUser.mid !=null }"> <font color="blue">Mid: ${mobileUser.mid }</font></c:if></h3>

						<div class="row">
						<c:if test="${mobileUser.ezywiretidList !=null }">
								<div class="input-field col s12 m6 l6 ">
									
									<select  name="tid1"
								id="tid1" path="tid1">
								
								<option selected value=""><c:out value="TID" /></option>
								
								<c:forEach items="${mobileUser.ezywiretidList}" var="tid">
									<%-- <option <c:out value="${tid}"/>> --%>
									
									<option value="${pageContext.request.contextPath}/mobileUser/findTidUserDetails?tid=${tid}&mid=${mobileUser.mid}">
									
									
									${tid}
									</option>

								</c:forEach>
								</optgroup>
							</select>
							<label for="tid">Select TID</label>
								 </div>
							
							</c:if>
							
							<div class="input-field col s12 m6 l6 ">
									
									<input type="text" id="tid" value="${mobileUser.tid }"
												placeholder="tid" name="tid" path="tid"  />
									
								<label for="Email">TID</label>
							</div>
							<div class="input-field col s12 m6 l6 ">
								<label for="merchantusername">User Name</label>
								<input type="text" id="ezywiremobusername" value="${mobileUser.ezywiremobusername }"
									placeholder="ezywiremobusername" name="ezywiremobusername" path="merchantusername"
									readonly="true"/>
								
										
							</div>
							</div>
							
							
							</div>
							
					</div></div></div></div>

</c:if>
					<c:if test="${mobileUser.motoMid != null && mobileUser.motoTid == null}">
					<input type="hidden" value="${mobileUser.motoMid}" name="motoMid" path="motoMid"/>
					
					<div class="row" id="motoDiv">
  <div class="col s12">
      <div class="card border-radius">
        <div class="card-content padding-card">
		 <div class="d-flex align-items-center">
						<h3 >MobileUser EzyMoto Details <font color="blue"> Mid: ${mobileUser.motoMid}</font> </h3>
</div>
						<div class="row">
	
							<div class="input-field col s12 m6 l6 ">
									<label for="tid">TID</label>
									<label id="erremotoTid"><c:if test="${responseDatamototid != null}">
													<H4 id="motoTidErr" style="color: #ff4000;" align="center">${responseDatamototid}</H4>
												</c:if></label>
									<input type="text"  id="motoTid" value="${mobileUser.motoTid}"
												placeholder="motoTid" name="motoTid"  path="motoTid"
												onclick="document.getElementById('erremotoTid').style.display='none';"/>
											
									
								 </div>
							<div class="input-field col s12 m6 l6 ">
											<label for="Email">Device Id</label>
									<label id="motodeviceIdErr"><c:if test="${responseDatamotodeviceid != null}">
													<H4 id="" style="color: #ff4000;" align="center">${responseDatamotodeviceid}</H4>
												</c:if></label>
											<input type="text"  id="motodeviceId" value="${mobileUser.motodeviceId}"
												placeholder="motodeviceId" name="motodeviceId"  path="motodeviceId"
												onclick="document.getElementById('motodeviceIdErr').style.display='none';"/>
											
										</div>
							<div class="input-field col s12 m6 l6 ">
								
									<select name="motorefNo" path="motorefNo" value="${mobileUser.motorefNo}"
										id="motorefNo"  style="width:100%">
										<option selected value=""><c:out value="refNo" /></option>
										<c:forEach items="${refNoList}" var="refNo">
											<option value="${refNo}">${refNo}</option>


										</c:forEach>
									</select>
										<label for="Email">Reference No</label>
								</div>
							<div class="input-field col s12 m6 l6 ">
							<label for="Moto_Pre_Auth">Pre-Auth</label>
								<p>
								<c:choose>
								<c:when test="${mobileUser.motopreAuth == 'Yes' }">
								<label><input name="group1" path="motopreAuth" value="Yes" id="motopreAuth" type="radio" checked /><span>Yes</span></label>   
								<label><input name="group1"  value="No"  id="motopreAuth"  path="motopreAuth" type="radio" /><span>No</span></label>
								</c:when>
								<c:otherwise>
								<label><input name="group1" path="motopreAuth" value="Yes" id="motopreAuth" type="radio"  /><span>Yes</span></label>   
								<label><input name="group1"  value="No"  id="motopreAuth"  path="motopreAuth" type="radio" checked/><span>No</span></label>
								</c:otherwise>
								</c:choose>
								</p>
										</div>
									
							
							
						</div>
					</div></div></div></div>
					
					</c:if>

<!--  start ezyrec -->
<c:if test="${mobileUser.ezyrecMid != null && mobileUser.ezyrecTid == null}">
					<input type="hidden" value="${mobileUser.ezyrecMid}" name="ezyrecMid" path="ezyrecMid"/>
					
					<div class="row" id="ezyrecDiv">
  <div class="col s12">
      <div class="card border-radius">
        <div class="card-content padding-card">
		 <div class="d-flex align-items-center">
						<h3 >MobileUser EzyRec Details <font color="blue"> Mid: ${mobileUser.ezyrecMid}</font> </h3>
</div>
						<div class="row">

							<div class="input-field col s12 m6 l6 ">
									<label for="ezyrecTid">TID</label>
									<label id="errezyrecTid"><c:if test="${responseDataezyrectid != null}">
													<H4 id="ezyrecTidErr" style="color: #ff4000;" align="center">${responseDataezyrectid}</H4>
												</c:if></label>
									<input type="text" id="ezyrecTid" value="${mobileUser.ezyrecTid}"
												placeholder="ezyrecTid" name="ezyrecTid"  path="ezyrecTid"
												onclick="document.getElementById('errezyrecTid').style.display='none';"/>
											
									
								 </div>
							<div class="input-field col s12 m6 l6 ">
											<label for="ezyrecdeviceId">Device Id</label>
											<label id="ezyrecdeviceIdErr"><c:if test="${responseDataezyrecdeviceid != null}">
													<H4 id="" style="color: #ff4000;" align="center">${responseDataezyrecdeviceid}</H4>
												</c:if></label>
											<input type="text" id="ezyrecdeviceId" value="${mobileUser.ezyrecdeviceId}"
												placeholder="ezyrecdeviceId" name="ezyrecdeviceId"  path="ezyrecdeviceId"
												onclick="document.getElementById('ezyrecdeviceIdErr').style.display='none';"/>
											
										</div>
							<div class="input-field col s12 m6 l6 ">
									
									<select class="form-control" name="ezyrecrefNo" path="ezyrecrefNo" value="${mobileUser.ezyrecrefNo}"
										id="ezyrecrefNo"  style="width:100%">
										<option selected value=""><c:out value="refNo" /></option>
										<c:forEach items="${refNoList}" var="refNo">
											<option value="${refNo}">${refNo}</option>


										</c:forEach>
									</select>
									<label for="Email">Reference No</label>
								</div>
							</div>
							
							
							
						</div>
					</div></div></div>
					
					</c:if>
<!-- end ezyrec -->




			<c:if test="${mobileUser.ezypassMid !=null && mobileUser.ezypassTid ==null}">
					<input type="hidden" value="${mobileUser.ezypassMid}" name="ezypassMid" path="ezypassMid"/>
					
					<div class="row" id="ezypassDiv">
  <div class="col s12">
      <div class="card border-radius">
        <div class="card-content padding-card">
		 <div class="d-flex align-items-center">
						<h3 >MobileUser EzyPass Details <font color="blue">
						Mid:${mobileUser.ezypassMid}</font></h3>
</div>

							<div class="row">
							
							<div class="input-field col s12 m6 l6 ">
									<label for="tid">TID</label>
									<label id="ezypassTidErr"><c:if test="${responseDataezypasstid != null}">
											<H4 id="" style="color: #ff4000;" align="center">${responseDataezypasstid}</H4>
										</c:if></label>
									<input type="text"  id="ezypassTid" value="${mobileUser.ezypassTid}"
										placeholder="ezypassTid" name="ezypassTid"  path="ezypassTid"
										onclick="document.getElementById('ezypassTidErr').style.display='none';"/>
									
									
								 </div>
							<div class="input-field col s12 m6 l6 ">
											<label for="Email">Device Id</label>
										<label id="ezypassdeviceIdErr"><c:if test="${responseDataepassdeviceid != null}">
													<H4 id="" style="color: #ff4000;" align="center">${responseDataepassdeviceid}</H4>
												</c:if></label>
											<input type="text" id="ezypassdeviceId" value="${mobileUser.ezypassdeviceId}"
												placeholder="ezypassdeviceId" name="ezypassdeviceId"  path="ezypassdeviceId"
												onclick="document.getElementById('ezypassdeviceIdErr').style.display='none';"/>
											
										</div>
							<div class="input-field col s12 m6 l6 ">
									
									<select name="ezypassrefNo" path="ezypassrefNo"
										id="ezypassrefNo" style="width:100%" value="${mobileUser.ezypassrefNo}">
										<option selected value=""><c:out value="refNo" /></option>
										<c:forEach items="${refNoList}" var="refNo">
											<option value="${refNo}">${refNo}</option>


										</c:forEach>
									</select>
									<label for="Email">Reference No</label>
								</div>
								
							</div>
							
							
							
						</div>
					</div>
					</div></div>
					
					
					</c:if>
					<!-- start umtid -->		
					<c:if test="${mobileUser.um_mid !=null && mobileUser.um_tid ==null}">
					<input type="hidden" value="${mobileUser.um_mid}" name="um_mid" path="um_mid"/>
					
					<div class="row" id="ezypassDiv">
  <div class="col s12">
      <div class="card border-radius">
        <div class="card-content padding-card">
		 <div class="d-flex align-items-center">
						<h3 >MobileUser UM_MID Details <font color="blue">
						Mid:${mobileUser.um_mid}</font></h3>
</div>
						<div class="row">
							
							
							<div class="input-field col s12 m6 l6 ">
									<label for="tid">TID</label>
									<label id="erremotoTid"><c:if test="${responseDataumtid != null}">
													<H4 id="motoTidErr" style="color: #ff4000;" align="center">${responseDataumtid}</H4>
												</c:if></label>
									<input type="text"  id="um_tid" value="${mobileUser.um_tid}"
												placeholder="um_tid" name="um_tid"  path="um_tid"
												onclick="document.getElementById('erremotoTid').style.display='none';"/>
											
								 </div>
							<div class="input-field col s12 m6 l6 ">
											<label for="Email">Device Id</label>
											<label id="motodeviceIdErr"><c:if test="${responseDataumdeviceid != null}">
													<H4 id="" style="color: #ff4000;" align="center">${responseDataumdeviceid}</H4>
												</c:if></label>
											<input type="text"  id="um_deviceId" value="${mobileUser.um_deviceId}"
												placeholder="um_deviceId" name="um_deviceId"  path="um_deviceId"
												onclick="document.getElementById('motodeviceIdErr').style.display='none';"/>
											
										</div>
						
							
							<div class="input-field col s12 m6 l6 ">
									
									<select  name="um_refNo" path="um_refNo"
										id="um_refNo" style="width:100%" value="${mobileUser.um_refNo}">
										<option selected value=""><c:out value="refNo" /></option>
										<c:forEach items="${um_listRefNo}" var="um_refNo">
											<option value="${um_refNo}">${um_refNo}</option>


										</c:forEach>
									</select>
									<label for="um_refNo">Reference No</label>
								</div>
							
							
							
						</div></div></div></div></div>
						
		
					
					</c:if>
					


						<button class="submitBtn" type="submit"
							onclick="return loadSelectData()">Submit</button>
	</form>
					</div>
				


	</body>
	</html>
	
