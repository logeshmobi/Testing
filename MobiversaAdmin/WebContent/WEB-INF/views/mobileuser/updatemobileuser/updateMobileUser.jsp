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
 <!-- <script type="text/javascript" src="https://ajax.googleapis.com/ajax/libs/jquery/3.1.0/jquery.min.js"></script> -->
  
<%--  <script type="text/javascript" src="${pageContext.request.contextPath}/resourcesNew/js/plugins/select2.min.js"></script>
 <script type="text/javascript" src="${pageContext.request.contextPath}/resourcesNew/js/urlHide.js"></script>
  --%>
 <style>
.error {
	color: red;
	font-weight: bold;
}


</style>

</head>


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
       
       var businessName=document.getElementById("businessName").value;
       
       if(businessName == null || businessName==''){
       alert("Please Select BusinessName");
       return false;
       }
       
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
       <script>
    $(function(){
      // bind change event to select
      $('#tid2').on('change', function () {
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
      $('#tid3').on('change', function () {
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
      $('#tid4').on('change', function () {
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
      $('#tid7').on('change', function () {
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
      $('#tid9').on('change', function () {
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
<body onload="return displaydiv();">
	
	<div class="container-fluid">  
<div class="row">
  <div class="col s12">
      <div class="card blue-bg text-white">
        <div class="card-content">
          <div class="d-flex align-items-center">
           <h3 class="text-white">  <strong>Update Mobile User </strong></h3>
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
							<div class="input-field col s12 m3 l3">
								
									Business Name
									
							</div>	
					<div class="input-field col s12 m5 l5">
									<select name="merchantName"
								id="merchantName" path="merchantName"  class="browser-default select-filter">
								<optgroup label="Business Names" style="width:100%">
								<option selected value=""><c:out value="business Name" /></option>
								<c:forEach items="${merchant1}" var="merchant1">
								<c:if test="${merchant1.role == 'BANK_MERCHANT'}">
									<option value="${pageContext.request.contextPath}/mobileUser/findMobileuserDetails?id=${merchant1.id}">
		
									${merchant1.businessName}~${merchant1.username}~${merchant1.role}
									</option>
</c:if>
								</c:forEach>
								</optgroup>
							</select>						</div>	
							
							</div>
					
					<div class="row">
					 <div class="input-field col s12 m3 l3"></div>

        <!-- Script -->
        <script>
        $(document).ready(function(){
            
            // Initialize select2
            $("#selUser").select2();
            $(".select-filter").select2();
            
            
        });
        </script>
        
        
					
							<div class="input-field col s12 m3 l3 select-search">
			
<style>

.select2-dropdown {    border: 2px solid #2e5baa; }
.select2-container--default .select2-selection--single {border:none;}
 .select-search .select-wrapper input {
	display:none !important;
}

.select-search .select-wrapper input {
	display:none !important;
}
.select2-results__options li{
	list-style-type: none;												
	padding: 4px 0px 4px 4px !important;
	font-size: 18px !important;
}

ul.select2-results__options li{
	max-height:250px;
	
	curser:pointer;
 }
li ul .select2-results__option:hover{
	background-color: #005baa !important;
	
	color:#fff !important; 
}
.select-search-hidden .select2-container{
	display:none !important;
}

</style>
							<input type="text" class="shownSearch" path="businessName" id="businessName" readonly
								name="businessName" Placeholder="businessName" value="${mobileUser.businessName }"/>
							</div>
							
						</div>	</div>
						</div></div></div>
				<form method="GET" id="form1" action="${pageContext.request.contextPath}/mobileUser/updateMobileUserDetails"
			name="form1" commandName="mobileUser">		
						
					<c:if test="${mobileUser.mid !=null && mobileUser.ezywiretidList !=null}">
					<input type="hidden" value="${mobileUser.mid }" name="mid" path="mid"/>
					<div class="row">
  <div class="col s12">
      <div class="card border-radius">
        <div class="card-content padding-card">
		 <div class="d-flex align-items-center">
						<h3>MobileUser Ezywire Details 
						<c:if test="${mobileUser.mid !=null }"> <font color="blue">Mid: ${mobileUser.mid }</font></c:if></h3>
</div>
						<div class="row">
						<c:if test="${mobileUser.ezywiretidList !=null }">
							<div class="input-field col s12 m6 l6 ">
									
									<select name="tid1" class="browser-default select-filter"
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
							<div class="input-field col s12 m6 l6 ">
											<label for="Email">User Name</label>
											<input type="text"  id="merchantusername" value="${mobileUser.merchantusername }"
												placeholder="merchantusername" name="merchantusername" path="merchantusername"
												readonly="true"/>
											
										</div>
										</c:if>
							</div>
							
							
							
							
							<div class="row">
							<c:if test="${mobileUser.ezywiretidList ==null }">
							<div class="input-field col s12 m6 l6 ">
									<label for="Email">TID</label>
									<input type="text" id="tid" value="${mobileUser.tid }"
												placeholder="tid" name="tid" path="tid" 
												/>
								</div>
							
							<div class="input-field col s12 m6 l6 ">
											<label for="Email">Device Id</label>
											<input type="text" id="ezypassdeviceId" value="${mobileUser.ezypassdeviceId}"
												placeholder="ezypassdeviceId" name="ezypassdeviceId"  path="ezypassdeviceId"/>
											
												<c:if test="${responseData2!= null}">
													<H4 style="color: #ff4000;" align="center">${responseData2}</H4>
												</c:if>
											
										</div>
							<div class="input-field col s12 m6 l6 ">
									
									<select class="browser-default select-filter" name="referenceNo" path="referenceNo"
										id="referenceNo"  style="width:100%" value="${mobileUser.referenceNo }">
										<option selected value=""><c:out value="refNo" /></option>
										<c:forEach items="${refNoList}" var="refNo">
											<option value="${refNo}">${refNo}</option>


										</c:forEach>
									</select>
									<label for="Email">Reference No</label>
								</div>
							<div class="input-field col s12 m6 l6 ">
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
								
								
							</c:if>
							</div>
							</div>
							</div>
						</div>
						</div></c:if>
						

					<c:if test="${mobileUser.motoMid != null && mobileUser.mototidList !=null}">
					<input type="hidden" value="${mobileUser.motoMid}" name="motoMid" path="motoMid"/>
				
					<div class="row"  id="motoDiv">
  <div class="col s12">
      <div class="card border-radius">
        <div class="card-content padding-card">
		 <div class="d-flex align-items-center">
						<h3>MobileUser EzyMoto Details <font color="blue"> Mid: ${mobileUser.motoMid}</font> </h3>
</div>
						<div class="row">
						<c:if test="${mobileUser.mototidList !=null}">
								<div class="input-field col s12 m6 l6 ">
									
									<select name="tid2" class="browser-default select-filter"
								id="tid2" path="tid2">
								
								<option selected value=""><c:out value="MOTO TID" /></option>
								
								<c:forEach items="${mobileUser.mototidList}" var="motoTid">
									<%-- <option <c:out value="${tid}"/>> --%>
									
									<option value="${pageContext.request.contextPath}/mobileUser/findMotoTidUserDetails?motoTid=${motoTid}&motoMid=${mobileUser.motoMid}">
									
									
									${motoTid}
									</option>

								</c:forEach>
								</optgroup>
							</select>
							<label for="tid">Select TID</label>
								 </div>
								 <div class="input-field col s12 m6 l6 ">
											<label for="Email">User Name</label>
											<input type="text"  id="merchantusername" value="${mobileUser.merchantusername }"
												placeholder="merchantusername" name="merchantusername" path="merchantusername"
												readonly="true"/>
											
										</div>
							
								 </c:if></div>
							
							
							<c:if test="${mobileUser.mototidList ==null}">
							<div class="input-field col s12 m6 l6 ">
									<label for="tid">TID</label>
									<input type="text"  id="motoTid" value="${mobileUser.motoTid}"
												placeholder="motoTid" name="motoTid"  path="motoTid"/>
											
												<c:if test="${responseData2!= null}">
													<H4 style="color: #ff4000;" align="center">${responseData2}</H4>
												</c:if>
											
									
								 
							</div>
							<div class="input-field col s12 m6 l6 ">
											<label for="Email">Device Id</label>
											<input type="text" id="motodeviceId" value="${mobileUser.motodeviceId}"
												placeholder="motodeviceId" name="motodeviceId"  path="motodeviceId"/>
											
												<c:if test="${responseData2!= null}">
													<H4 style="color: #ff4000;" align="center">${responseData2}</H4>
												</c:if>
											
										
							</div>
							
							<div class="input-field col s12 m6 l6 ">
									
									<select class="browser-default select-filter" name="motorefNo" path="motorefNo" value="${mobileUser.motorefNo}"
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
									</c:if>
								</div>
								</div></div></div>
							
								</c:if>
						
					
					
					
					
				


<!--  start-->
	<c:if test="${mobileUser.ezyrecMid != null && mobileUser.ezyrectidList !=null}">
					<input type="hidden" value="${mobileUser.ezyrecMid}" name="ezyrecMid" path="ezyrecMid"/>
				
					<div class="row"  id="ezyrecDiv">
  <div class="col s12">
      <div class="card border-radius">
        <div class="card-content padding-card">
		 <div class="d-flex align-items-center">
						<h3 >MobileUser EzyRec Details <font color="blue"> Mid: ${mobileUser.ezyrecMid}</font> </h3>
</div>
						<div class="row">
						<c:if test="${mobileUser.ezyrectidList !=null}">
							<div class="input-field col s12 m6 l6 ">
									
									<select  name="tid4"
								id="tid4" path="tid4" class="browser-default select-filter">
								
								<option selected value=""><c:out value="EZYREC TID" /></option>
								
								<c:forEach items="${mobileUser.ezyrectidList}" var="ezyrecTid">
									<%-- <option <c:out value="${tid}"/>> --%>
									
									<option value="${pageContext.request.contextPath}/mobileUser/findEzyRecTidUserDetails?ezyrecTid=${ezyrecTid}&ezyrecMid=${mobileUser.ezyrecMid}">
									
									
									${ezyrecTid}
									</option>

								</c:forEach>
								</optgroup>
							</select>
							<label for="tid">Select TID</label>
								 </div>
								 
								<div class="input-field col s12 m6 l6 ">
											<label for="Email">User Name</label>
											<input type="text"  id="merchantusername" value="${mobileUser.merchantusername }"
												placeholder="merchantusername" name="merchantusername" path="merchantusername"
												readonly="true"/>
											
										</div>
							
								 </c:if>
							
							
							<c:if test="${mobileUser.ezyrectidList ==null}">
							<div class="input-field col s12 m6 l6 ">
									<label for="tid">TID</label>
									<input type="text" id="motoTid" value="${mobileUser.ezyrecTid}"
												placeholder="ezyrecTid" name="ezyrecTid"  path="ezyrecTid"/>
											
												<c:if test="${responseData2!= null}">
													<H4 style="color: #ff4000;" align="center">${responseData2}</H4>
												</c:if>
											
									
								 </div>
							<div class="input-field col s12 m6 l6 ">
											<label for="ezyrecdeviceId">Device Id</label>
											<input type="text" id="motodeviceId" value="${mobileUser.ezyrecdeviceId}"
												placeholder="ezyrecdeviceId" name="ezyrecdeviceId"  path="ezyrecdeviceId"/>
											<div>
												<c:if test="${responseData2!= null}">
													<H4 style="color: #ff4000;" align="center">${responseData2}</H4>
												</c:if>
											</div>
										</div>
							<div class="input-field col s12 m6 l6 ">
									
									<select  name="ezyrecrefNo" path="ezyrecrefNo" value="${mobileUser.ezyrecrefNo}"
										id="ezyrecrefNo"  style="width:100%">
										<option selected value=""><c:out value="refNo" /></option>
										<c:forEach items="${refNoList}" var="refNo">
											<option value="${refNo}">${refNo}</option>


										</c:forEach>
									</select>
									<label for="Email">Reference No</label>
								</div>
							<div class="input-field col s12 m6 l6 ">
										<label for="Ezyrec_Pre_Auth">Pre-Auth</label>
										<p>
								<c:choose>
								<c:when test="${mobileUser.motopreAuth == 'Yes' }">
								<label><input name="group1" path="ezyrecpreAuth" value="Yes" id="motopreAuth" type="radio" checked /><span>Yes</span></label>   
								<label><input name="group1"  value="No"  id="ezyrecpreAuth"  path="motopreAuth" type="radio" /><span>No</span></label>
								</c:when>
								<c:otherwise>
								<label><input name="group1" path="ezyrecpreAuth" value="Yes" id="motopreAuth" type="radio"  /><span>Yes</span></label>   
								<label><input name="group1"  value="No"  id="ezyrecpreAuth"  path="motopreAuth" type="radio" checked/><span>No</span></label>
								</c:otherwise>
								</c:choose>
								</p>

									</div>
								
							
							</c:if>
						</div>
					</div></div></div></div>
					
					
					
					</c:if>


<!-- end -->


			<c:if test="${mobileUser.ezypassMid !=null && mobileUser.ezypasstidList !=null }">
					<input type="hidden" value="${mobileUser.ezypassMid}" name="ezypassMid" path="ezypassMid"/>
					
<div class="row"  id="ezypassDiv">
  <div class="col s12">
      <div class="card border-radius">
        <div class="card-content padding-card">
		 <div class="d-flex align-items-center">
						<h3 >MobileUser EzyPass Details <font color="blue">
						Mid:${mobileUser.ezypassMid}</font></h3>
</div>
						<div class="row">
							<c:if test="${mobileUser.ezypasstidList !=null }">
							<div class="input-field col s12 m6 l6 ">
									
									<select  name="tid3" class="browser-default select-filter"
								id="tid3" path="tid3">
								
								<option selected value=""><c:out value="EZYPASS TID" /></option>
								
								<c:forEach items="${mobileUser.ezypasstidList}" var="ezypassTid">
									<%-- <option <c:out value="${tid}"/>> --%>
									
									<option value="${pageContext.request.contextPath}/mobileUser/findEzypassTidUserDetails?ezypassTid=${ezypassTid}&ezypassMid=${mobileUser.ezypassMid}">
									
									
									${ezypassTid}
									</option>

								</c:forEach>
								</optgroup>
							</select>
							<label for="tid">Select TID</label>
								 </div>
								 <div class="input-field col s12 m6 l6 ">
											<label for="Email">User Name</label>
											<input type="text" class="form-control" id="merchantusername" value="${mobileUser.merchantusername }"
												placeholder="merchantusername" name="merchantusername" path="merchantusername"
												readonly="true"/>
											
										</div>
							
							</c:if>
							
							<c:if test="${mobileUser.ezypasstidList ==null }">
							<div class="input-field col s12 m6 l6 ">
									<label for="tid">TID</label>
									<input type="text" class="form-control" id="ezypassTid" value="${mobileUser.ezypassTid}"
										placeholder="ezypassTid" name="ezypassTid"  path="ezypassTid"/>
									
										<c:if test="${responseData1 != null}">
											<H4 style="color: #ff4000;" align="center">${responseData1}</H4>
										</c:if>
									
								 </div>
							<div class="input-field col s12 m6 l6 ">
											<label for="Email">Device Id</label>
											<input type="text" id="ezypassdeviceId" value="${mobileUser.ezypassdeviceId}"
												placeholder="ezypassdeviceId" name="ezypassdeviceId"  path="ezypassdeviceId"/>
											
												<c:if test="${responseData2!= null}">
													<H4 style="color: #ff4000;" align="center">${responseData2}</H4>
												</c:if>
											
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
							
							
							
							</c:if>
							</div>
						</div>
					</div>
					
					</div></div>
					
					</c:if>
					
					
					<c:if test="${mobileUser.um_motoMid !=null && mobileUser.um_mototidList !=null }">
					<input type="hidden" value="${mobileUser.um_motoMid}" name="ezypassMid" path="ezypassMid"/>
					
<div class="row"  id="umEzymotoDiv">
  <div class="col s12">
      <div class="card border-radius">
        <div class="card-content padding-card">
		 <div class="d-flex align-items-center">
						<h3 >MobileUser UMMoto Details <font color="blue">
						Mid:${mobileUser.um_motoMid}</font></h3>
</div>
						<div class="row">
							<c:if test="${mobileUser.um_motoMid !=null }">
							<div class="input-field col s12 m6 l6 ">
									
									<select  name="tid7" class="browser-default select-filter"
								id="tid7" path="tid7">
								
								<option selected value=""><c:out value="UM MOTO TID" /></option>
								
								<c:forEach items="${mobileUser.um_mototidList}" var="um_motoTid">
									<%-- <option <c:out value="${tid}"/>> --%>
									
									<option value="${pageContext.request.contextPath}/mobileUser/findUmMotoTidUserDetails?umMotoTid=${um_motoTid}&umMotoMid=${mobileUser.um_motoMid}">
									
									
									${um_motoTid}
									</option>

								</c:forEach>
								</optgroup>
							</select>
							<label for="tid">Select TID</label>
								 </div>
								 <div class="input-field col s12 m6 l6 ">
											<label for="Email">User Name</label>
											<input type="text" class="form-control" id="merchantusername" value="${mobileUser.merchantusername }"
												placeholder="merchantusername" name="merchantusername" path="merchantusername"
												readonly="true"/>
											
										</div>
							
							</c:if>
							
							<c:if test="${mobileUser.um_mototidList ==null }">
							<div class="input-field col s12 m6 l6 ">
									<label for="tid">TID</label>
									<input type="text" class="form-control" id="um_motoTid" value="${mobileUser.motoTid}"
										placeholder="um_motoTid" name="um_motoTid"  path="um_motoTid"/>
									
										<c:if test="${responseData1 != null}">
											<H4 style="color: #ff4000;" align="center">${responseData1}</H4>
										</c:if>
									
								 </div>
	
							
							
								<div class="input-field col s12 m6 l6 ">
										<label for="Email">Device Id</label>
										<input type="text" id="um_motodeviceId" value="${mobileUser.motodeviceId}"
											placeholder="um_motodeviceId" name="um_motodeviceId"  path="um_motodeviceId"/>
										
											<c:if test="${responseData2!= null}">
												<H4 style="color: #ff4000;" align="center">${responseData2}</H4>
											</c:if>
										
										</div>
										</c:if>
							</div>
						</div>
					</div>
					
					</div></div>
					
					</c:if>
					
					
					<c:if test="${mobileUser.um_ezywayMid !=null && mobileUser.um_ezywaytidList !=null }">
					<input type="hidden" value="${mobileUser.um_ezywayMid}" name="ezywayMid" path="ezywayMid"/>
					
<div class="row"  id="umEzywayDiv">
  <div class="col s12">
      <div class="card border-radius">
        <div class="card-content padding-card">
		 <div class="d-flex align-items-center">
						<h3 >MobileUser UMEzyway Details <font color="blue">
						Mid:${mobileUser.um_ezywayMid}</font></h3>
</div>
						<div class="row">
							<c:if test="${mobileUser.um_ezywayMid !=null }">
							<div class="input-field col s12 m6 l6 ">
									
									<select  name="tid9" class="browser-default select-filter"
								id="tid9" path="tid9">
								
								<option selected value=""><c:out value="UM EZYWAY TID" /></option>
								
								<c:forEach items="${mobileUser.um_ezywaytidList}" var="um_ezywayTid">
									<%-- <option <c:out value="${tid}"/>> --%>
									
									<option value="${pageContext.request.contextPath}/mobileUser/findUmEzywayTidUserDetails?umEzywayTid=${um_ezywayTid}&umEzywayMid=${mobileUser.um_ezywayMid}">
									
									
									${um_ezywayTid}
									</option>

								</c:forEach>
								</optgroup>
							</select>
							<label for="tid">Select TID</label>
								 </div>
								 <div class="input-field col s12 m6 l6 ">
											<label for="Email">User Name</label>
											<input type="text" class="form-control" id="merchantusername" value="${mobileUser.merchantusername }"
												placeholder="merchantusername" name="merchantusername" path="merchantusername"
												readonly="true"/>
											
										</div>
							
							</c:if>
							
							<c:if test="${mobileUser.um_ezywaytidList ==null }">
							<div class="input-field col s12 m6 l6 ">
									<label for="tid">TID</label>
									<input type="text" class="form-control" id="um_ezywayTid" value="${mobileUser.ezywayTid}"
										placeholder="um_ezywayTid" name="um_ezywayTid"  path="um_ezywayTid"/>
									
										<c:if test="${responseData1 != null}">
											<H4 style="color: #ff4000;" align="center">${responseData1}</H4>
										</c:if>
									
								 </div>
	
							
							
								<div class="input-field col s12 m6 l6 ">
										<label for="Email">Device Id</label>
										<input type="text" id="um_ezywaydeviceId" value="${mobileUser.ezywaydeviceId}"
											placeholder="um_ezywaydeviceId" name="um_ezywaydeviceId"  path="um_ezywaydeviceId"/>
										
											<c:if test="${responseData2!= null}">
												<H4 style="color: #ff4000;" align="center">${responseData2}</H4>
											</c:if>
										
										</div>
										</c:if>
							</div>
						</div>
					</div>
					
					</div></div>
					
					</c:if>
					
					
					
					

	</form>
					</div>
				
<script type="text/javascript">
jQuery(document).ready(function() {
$('#merchantName').select2();
/* $('#referenceNo').select2();
$('#motorefNo').select2();
$('#ezypassrefNo').select2();
$('#ezyrecrefNo').select2();

$('#connectType').select2();
$('#deviceType').select2();
$('#tid1').select2();
$('#tid2').select2();
$('#tid3').select2();
$('#tid4').select2(); */
/* $('#motoTid').select2(); */
//$('#renewalPeriod').select2();
});  
    </script>

	</body>
	</html>
	
