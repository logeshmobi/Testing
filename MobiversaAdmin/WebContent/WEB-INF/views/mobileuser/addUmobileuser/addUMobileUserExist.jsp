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


<!-- <script type="text/javascript">
jQuery(document).ready(function() {
$('#merchantName').select2();
$('#um_refNo').select2();

$('#um_motorefNo').select2();
$('#um_ezypassrefNo').select2();
$('#um_ezyrecrefNo').select2();
$('#um_ezywayrefNo').select2();

$('#connectType').select2();
$('#deviceType').select2();
//$('#renewalPeriod').select2();
});  
    </script>
 -->


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

       function checkDevice(){
    		//alert("checking device-----------");
    		var tid = document.getElementById("tid").value;
    		var e = document.getElementById("referenceNo").value;
    		var deviceId = document.getElementById("deviceId").value;

    			if (tid != '' && tid != null) {
    				//alert("check tid valid");
    				if (!allnumeric(document.form1.tid, 8, 8)) {
    					return false;
    				}

    				if (deviceId != '' && deviceId != null) {
    					if (!alphanumeric(document.form1.deviceId, 15, 16)) {
    						return false;
    					}
    				} else {
    					alert("Please Select DeviceId");
    					return false;
    				}
    				
    				if (e == null || e == '' || e == 'refNo') {

    					alert("Please Select Reference No");
    					//form.submit = false; 
    					return false;
    				}
    			}
    			return true;
    		
    	}
    	function checkMotoDevice(){
    		//alert("checking moto device-----------");
    		var motoTid = document.getElementById("motoTid").value;
    		var motorefNo = document.getElementById("motorefNo").value;
    		var motodeviceId = document.getElementById("motodeviceId").value;

    		if (motoTid != '' && motoTid != null) {
    			if (!allnumeric(document.form1.motoTid, 8, 8)) {
    				return false;
    			}

    			if (motodeviceId != '' && motodeviceId != null) {
    				if (!alphanumeric(document.form1.motodeviceId, 15, 16)) {
    					return false;
    				}
    			} else {
    				alert("Please Select Moto DeviceId");
    				return false;
    			}
    			
    			if (motorefNo == null || motorefNo == ''
    				|| motorefNo == 'refNo') {

    			alert("Please Select Moto Reference No");
    			//form.submit = false; 
    			return false;
    			}
    			}
    		return true;
    	}

    	function checkEzypassDevice(){
    		//alert("checking ezypass device-----------");
    		var ezypassTid = document.getElementById("ezypassTid").value;
    		var ezypassrefNo = document.getElementById("ezypassrefNo").value;
    		var ezypassdeviceId = document.getElementById("ezypassdeviceId").value;

    		if (ezypassTid != '' && ezypassTid != null) {
    			if (!allnumeric(document.form1.ezypassTid, 8, 8)) {
    				return false;
    			}

    			if (ezypassdeviceId != '' && ezypassdeviceId != null) {
    				if (!alphanumeric(document.form1.ezypassdeviceId, 15, 16)) {
    					return false;
    				}
    			} else {
    				alert("Please Select Ezypass DeviceId");
    				return false;
    			}
    			
    			if (ezypassrefNo == null || ezypassrefNo == ''
    				|| ezypassrefNo == 'refNo') {

    			alert("Please Select Ezypass Reference No");
    			//form.submit = false; 
    			return false;
    			}
    		}
    		return true;
    	}

    	function checkEzyrecDevice(){
    		//alert("checking ezyrec device-----------");
    		var ezyrecTid = document.getElementById("ezyrecTid").value;
    		var ezyrecrefNo = document.getElementById("ezyrecrefNo").value;
    		var ezyrecdeviceId = document.getElementById("ezyrecdeviceId").value;

    		if (ezyrecTid != '' && ezyrecTid != null) {
    			if (!allnumeric(document.form1.ezyrecTid, 8, 8)) {
    				return false;
    			}

    			if (ezyrecdeviceId != '' && ezyrecdeviceId != null) {
    				if (!alphanumeric(document.form1.ezyrecdeviceId, 15, 16)) {
    					return false;
    				}
    			} else {
    				alert("Please Select Ezyrec DeviceId");
    				return false;
    			}
    			
    			if (ezyrecrefNo == null || ezyrecrefNo == ''
    				|| ezyrecrefNo == 'refNo') {

    			alert("Please Select Ezyrec Reference No");
    			//form.submit = false; 
    			return false;
    		}

    		
    		}
    		return true;
    		
    	}


    	function validateEzyWayDevice(){
    		//alert("checking device-----------");
    		var ezywayTid = document.getElementById("ezywayTid").value;
    		var ezywayrefNo = document.getElementById("ezywayrefNo").value;
    		var ezywaydeviceId = document.getElementById("ezywaydeviceId").value;

    			if (ezywayTid != '' && ezywayTid != null) {
    				//alert("check tid valid");
    				if (!allnumeric(document.form1.ezywayTid, 8, 8)) {
    					return false;
    				}

    				if (ezywaydeviceId != '' && ezywaydeviceId != null) {
    					if (!alphanumeric(document.form1.ezywaydeviceId, 15, 16)) {
    						return false;
    					}
    				} else {
    					alert("Please Select EzyWay DeviceId");
    					return false;
    				}
    				
    				if (ezywayrefNo == null || ezywayrefNo == '' || ezywayrefNo == 'refNo') {

    					alert("Please Select EzyWay Ref No");
    					//form.submit = false; 
    					return false;
    				}
    			}
    			return true;
    		
    	}


    	function validateFormData(){
    		alert("validate form data");
    		
    		
    		if(!checkDevice()){
    			return false;
    		}
    		if(!checkMotoDevice()){
    			return false;
    		}
    		
    		if(!validateEzyWayDevice()){
    			return false;
    		}
    		if(!checkEzyrecDevice())
    			{
    			return false;
    			}
    		if(!checkEzypassDevice()){
    			return false;
    		}
    		//alert("check ezyway:"+validateEzyWayDevice());
    		
    		
    		 return true;
    	}

    	function validate(){
//    		alert("check validation: "+validateFormData());
    		if(validateFormData()){
    			return true;
    		}else{
    			return false;
    		}
    	}

      function loadSelectData()
       { 
       
    		var expiryDate = document.getElementById("expiryDate").value;
			if (expiryDate == '' || expiryDate == null) {
				alert("please fill Expiry Date");
				document.getElementById("expiryDate").focus();
				return false;

			}
			var renewalPeriod = document.getElementById("renewalPeriod").value;

			if (renewalPeriod == null || renewalPeriod == '') {

				alert("Please Fill Renewal Period");
				document.getElementById("renewalPeriod").focus();
				//form.submit = false; 
				return false;
			}
			var businessName = document.getElementById("businessName").value;
			if (businessName == '' || businessName == null) {

				alert("Please Fill BusinessName..");
				document.getElementById("businessName").focus();
				return false;

			}

			var contactName = document.getElementById("contactName").value;
			if (contactName == '' || contactName == null) {
				alert("Please Fill ContactName..");
				document.getElementById("contactName").focus();
				return false;

			}

			var tid = document.getElementById("tid").value;
			//alert("tid: "+tid);
			var motoTid = document.getElementById("motoTid").value;
			//alert("motoTid: "+motoTid);
			var ezypassTid = document.getElementById("ezypassTid").value;
			//alert("ezypassTid: "+ezypassTid);
			var ezyrecTid = document.getElementById("ezyrecTid").value;
			//alert("ezyrecTid: "+ezyrecTid);
			var ezywayTid = document.getElementById("ezywayTid").value;
			//alert("ezywayTid: "+ezywayTid);
			
			
			if((tid == '' || tid == null) && (motoTid == '' || motoTid == null) && 
					(ezypassTid == '' || ezypassTid == null) && (ezywayTid == '' || ezywayTid == null) &&
					(ezyrecTid == '' || ezyrecTid == null)){
				alert("No TID to Register..Please Fill Anyone TID Details..");
				return false;
			}
			
			var e = document.getElementById("referenceNo").value;
			var deviceId = document.getElementById("deviceId").value;

			if (tid != '' && tid != null) {
				//alert("check tid valid");
				if (!allnumeric(document.form1.tid, 8, 8)) {
					document.getElementById("tid").focus();
					return false;
				}

				if (deviceId != '' && deviceId != null) {
					if (!alphanumeric(document.form1.deviceId, 15, 16)) {
						document.getElementById("deviceId").focus();
						return false;
					}
				} else {
					alert("Please Select DeviceId");
					document.getElementById("deviceId").focus();
					return false;
				}

				if (e == null || e == '' || e == 'refNo') {

					alert("Please Select Reference No");
					document.getElementById("e").focus();
					//form.submit = false; 
					return false;
				}
			}
			
			
			var motorefNo = document.getElementById("motorefNo").value;
			var motodeviceId = document.getElementById("motodeviceId").value;

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
					alert("Please Select Moto DeviceId");
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
			
			
			var ezypassrefNo = document.getElementById("ezypassrefNo").value;
			var ezypassdeviceId = document.getElementById("ezypassdeviceId").value;

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
					alert("Please Select Ezypass DeviceId");
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
			
			var ezywayrefNo = document.getElementById("ezywayrefNo").value;
			var ezywaydeviceId = document.getElementById("ezywaydeviceId").value;

			//alert(ezywayrefNo);
			if (ezywayTid != '' && ezywayTid != null) {
				if (!allnumeric(document.form1.ezywayTid, 8, 8)) {
					document.getElementById("ezywayTid").focus();
					return false;
				}

				if (ezywaydeviceId != '' && ezywaydeviceId != null) {
					
					if (!alphanumeric(document.form1.ezywaydeviceId, 15, 16)) {
						document.getElementById("ezywaydeviceId").focus();
						return false;
					}
				} else {
					alert("Please Select Ezyway DeviceId");
					document.getElementById("ezywaydeviceId").focus();
					return false;
				}

				if (ezywayrefNo == null || ezywayrefNo == ''
						|| ezywayrefNo == 'refNo') {

					alert("Please Select Ezyway Reference No");
					document.getElementById("ezywayrefNo").focus();
					//form.submit = false; 
					return false;
				}
			}
			
			
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
					alert("Please Select Ezyrec DeviceId");
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

			
			return true;
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
<div class="container-fluid">    
  <div class="row">
  <div class="col s12">
      <div class="card blue-bg text-white">
        <div class="card-content">
          <div class="d-flex align-items-center">
           <h3 class="text-white">  <strong> Add UMobile User </strong></h3>
          </div>
          
          
        </div>
      </div>
    </div>
    </div>	
<div class="row">
	
			<c:if test="${ responseEmptyTID!=null }">
			
  				<center>  
  					<p id="emtpyTID"> <h3 style="color:red;">* Empty TID Details.. Please Fill Anyone TID Details...</h3></p></center>
 			</c:if>
 			<c:if test="${responseEmptyDeviceID!=null }">
			
  				<center>  
  					<p id="emtpyDeviceID"> <h3 style="color:red;">* Empty DeviceID Details..</h3></p></center>
 			</c:if>
 			
	<div class="col s12">
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
									<option value="${pageContext.request.contextPath}/mobileUser/finduserDetails?id=${merchant1.id}">
									
									
									<%-- <c:out value="${merchant1.mid.mid}~${merchant1.businessName}~${merchant1.role}"> --%>
									<%-- ${merchant1.id}~${merchant1.businessName}~${merchant1.username}~${merchant1.role} --%>
									
									${merchant1.businessName}~${merchant1.email}~${merchant1.merchantType}<%-- </c:out> --%>
									</option>

								</c:forEach>
								</optgroup>
							</select>
							<input type="text" class="form-control" id="businessName" readonly
								name="businessName" Placeholder="businessName" value="${mobileUser.businessName }"/>
						</div>
							
						</div>	</div>
						</div></div></div></div>
<style>
.drop-details .select-wrapper input.select-dropdown  { 
    border: 1px solid #005baa;     border-radius: 16px; padding: 0 12px; color:#005baa;
}

.drop-details .select-wrapper .caret {
    fill: #005baa;
}
</style> 
<form method="GET" id="form1" action="${pageContext.request.contextPath}/mobileUser/regUMobileUserDetails"
			name="form1" commandName="mobileUser">		
						
<input type="hidden" path="businessName" id="businessName" readonly
			name="businessName" Placeholder="businessName" value="${mobileUser.businessName }"/>
			<input type="hidden" path="merchantType" id="merchantType" readonly
name="merchantType" Placeholder="merchantType" value="${mobileUser.merchantType }"/>
		<c:if test="${mobileUser.businessName !=null }">			
<div class="row">
  <div class="col s12">
      <div class="card border-radius">
        <div class="card-content padding-card">
		 <div class="d-flex align-items-center">
		 
		  <ul class="tab_ul">
		   <c:if test="${mobileUser.um_mid !=null && !empty mobileUser.um_mid}">
		   <li id="umezywire">UM Ezywire Details</li>
		    </c:if>
		    <c:if test="${mobileUser.um_motoMid !=null && !empty mobileUser.um_motoMid}">
		    <li id="umezymoto">UM Ezymoto Details</li>
		    </c:if>
		    <c:if test="${mobileUser.um_ezywayMid !=null && !empty mobileUser.um_ezywayMid}">
		    <li id="umezyway">UM Ezyway Details</li>
		    </c:if>
		    <c:if test="${mobileUser.um_ezyrecMid !=null && !empty mobileUser.um_ezyrecMid}">
		    <li id="umezyrec">UM Ezyrec Details</li>
		    </c:if>
		    <c:if test="${mobileUser.um_ezypassMid !=null && !empty mobileUser.um_ezypassMid}">
		    <li id="umezypass">UM Ezypass Details</li>
		    </c:if>
		   </ul>
		   
          </div>
		  
		  <script>
 $(function() {                       
  $(".tab_ul li").click(function() {   
    $(".tab_ul li").removeClass("tb_active");      
    $(this).addClass("tb_active"); 
    id = $(this).attr("id"); 
   $('.content_area .row').hide();
   $('.'+id).show();
	
  });
});


 
$('.datepicker').pickadate();

</script>
		  
		   <style>
		   .tab_ul { display:flex;width: 100%;}
		   .tab_ul li { display: flex;
    /* margin-left: 10px; */
    padding: 10px 20px;
    background-color: #eeeae9;
    width: 100%; cursor:pointer;
	 margin: auto;
    display: table;
	}
	
	.tb_active {  background-color: #005baa !important;
    color: #fff;  text-align:center}
	
	.content_area { padding:30px; }
	
	 .page-wrapper {
    min-height: calc(50vh - 64px);
} 
#submit-btn { background-color:#54b74a;color:#fff; border-radius:10px; margin:auto; display:table;  }
.mob-br { display:none; }


@media screen and (max-width:768px){
.tab_ul { display: block !important; width: auto;}
.tab_ul li { float: left; display:block;width:auto;  }
.mob-br { display:block;  white-space: pre-line; }

}
		   </style>
		  <div style="clear:both;"></div>
		  
          <div class="content_area">
          
   <div class="row mobileuser">
						<div class="input-field col s12 m6 l6 " id="name">
						<input type="text" path="contactName" id="contactName"
								name="contactName"  value="${mobileUser.contactName }"/>
						<label for="first_name">Contact Name</label>
					</div>
					<div class="input-field col s12 m6 l6 " id="name">
						<input id="renewalPeriod" type="text" 
						onblur="checkRenewal()" path="renewalPeriod" value="${mobileUser.renewalPeriod}">
						<label for="name">Renewal Period(In Months)</label>
					</div>
					<div class="input-field col s12 m6 l6 ">
						<input type="text"  id="remarks" path="remarks"
				placeholder="remarks" name="remarks"  value=""/>
						<label for="name">Remarks</label>
					</div>
					
					<div class="input-field col s12 m6 l6 ">
						<input type="text"  id="expiryDate" path="expiryDate"
				placeholder="expiryDate" name="expiryDate" value=""/>
						<label for="name">Expiry Date</label>
					</div>
				</div>
				
				<div class="row umezywire" style="display:none;">
				<input type="hidden" value="${mobileUser.um_mid }" name="um_mid" path="um_mid"/>
					<div class="col s12 m4 l6"><h5 style="color:#005baa">MobileUser UM Ezywire Details Mid: </h5></div>
				<div class="col s12 m4 l6"><h5 style="color:#005baa">${mobileUser.um_mid }</h5></div>
				
				
					<div class="input-field col s12 m6 l6 ">
						<label for="um_tid">TID</label>
						<label id="errtid"><c:if test="${resDataum_tid != null}">
							<H4 style="color: #ff4000;" align="center">${resDataum_tid}</H4>
						</c:if></label>
					<input type="text"  id="um_tid"
						placeholder="um_tid" name="tid" path="um_tid"  value="${mobileUser.um_tid }"
						onclick="document.getElementById('errtid').style.display='none';"/>
						
					 </div>
					
					<div class="input-field col s12 m6 l6 ">
						<label for="um_deviceId">Device Id</label>
						<label id="errdeviceId"><c:if test="${resDataum_deviceid != null}">
							<H4 style="color: #ff4000;" align="center">${resDataum_deviceid}</H4>
						</c:if></label>
					<input type="text" id="um_deviceId" value="${mobileUser.um_deviceId}"
						placeholder="um_deviceId" name="um_deviceId" path="um_deviceId"
						onclick="document.getElementById('errdeviceId').style.display='none';"/>
					</div>
				
				
				<div class="input-field col s12 m6 l6 ">
				<select  name="um_refNo" path="um_refNo"
						id="um_refNo"  style="width:100%" value="">
						<option selected value=""><c:out value="um_refNo" /></option>
						<c:forEach items="${umrefNoList}" var="refNo">
							<option value="${refNo}">${refNo}</option>


						</c:forEach>
					</select>
				<label for="um_refNo">Reference No</label>
				</div>
				
				<div class="input-field col s12 m6 l6 "> Pre-Auth :
						<p><c:choose>
						<c:when test="${mobileUser.preAuth == 'Yes' }">
						<label><input name="group1"path="um_preAuth"
						value="Yes" id="um_preAuth"  type="radio" checked /><span>Yes</span></label>   
						<label><input name="group1" value="No"  id="um_preAuth" 
						 path="um_preAuth" type="radio" /><span>No</span></label>
						 </c:when>
						<c:otherwise>
						<label><input name="group1"path="um_preAuth"
						value="Yes" id="um_preAuth"  type="radio" /><span>Yes</span></label>   
						<label><input name="group1" value="No"  id="um_preAuth" 
						 path="um_preAuth" type="radio" checked /><span>No</span></label>
						 </c:otherwise>
				</c:choose></p>
   		 
					</div>
				</div>
				
				<div class="row umezymoto" style="display:none;">
				<input type="hidden" value="${mobileUser.um_motoMid}" name="um_motoMid" path="um_motoMid"/>
					<div class="col s12 m4 l6"><h5 style="color:#005baa">MobileUser UM Ezymoto Details Mid: </h5></div>
				<div class="col s12 m4 l6"><h5 style="color:#005baa">${mobileUser.um_motoMid }</h5></div>
				
				
					<div class="input-field col s12 m6 l6 ">
						<label for="um_motoTid">TID</label>
						<label id="errmotoTid"><c:if test="${resDataum_mototid != null}">
							<H4 style="color: #ff4000;" align="center">${resDataum_mototid}</H4>
						</c:if></label>
					<input type="text" id="um_motoTid" value="${mobileUser.um_motoTid}"
						placeholder="um_motoTid" name="um_motoTid"  path="um_motoTid"
						onclick="document.getElementById('errmotoTid').style.display='none';"/>					
					 </div>
					
					<div class="input-field col s12 m6 l6 ">
						<label for="um_motodeviceId">Device Id</label>
						<label id="errmotodeviceId"><c:if test="${resDataum_motodeviceid != null }">
							<H4 style="color: #ff4000;" align="center">${resDataum_motodeviceid}</H4>
						</c:if></label>
					<input type="text" id="um_motodeviceId" value="${mobileUser.um_motodeviceId}"
						placeholder="um_motodeviceId" name="um_motodeviceId"  path="um_motodeviceId"
						onclick="document.getElementById('errmotodeviceId').style.display='none';"/>					
					</div>
					
					
				
				
				<div class="input-field col s12 m6 l6 ">
				
				<select name="um_motorefNo" path="um_motorefNo" value=""
					id="um_motorefNo"  style="width:100%">
					<option selected value=""><c:out value="um_motorefNo" /></option>
					<c:forEach items="${umrefNoList}" var="refNo">
						<option value="${refNo}">${refNo}</option>
					</c:forEach>
				</select>
				<label for="um_motorefNo">Reference No</label>
				</div>
				
				<div class="input-field col s12 m6 l6 "> Pre-Auth :
				
						<p><c:choose>
						<c:when test="${mobileUser.motopreAuth == 'Yes' }">
						<label><input name="group1" path="motopreAuth"
						value="Yes" id="motopreAuth"   type="radio" checked /><span>Yes</span></label>   
						<label><input name="group1" value="No"  
						id="motopreAuth"  path="motopreAuth" type="radio" /><span>No</span></label>
                        </c:when>
                        <c:otherwise>   
                        <label><input name="group1" path="motopreAuth"
						value="Yes" id="motopreAuth"   type="radio" checked /><span>Yes</span></label>   
						<label><input name="group1" value="No"  
						id="motopreAuth"  path="motopreAuth" type="radio" /><span>No</span></label>
						</c:otherwise>
						</c:choose></p> 
                 		 
					</div>
				</div>
				
				<div class="row umezyway" style="display:none;">
				<input type="hidden" value="${mobileUser.um_ezywayMid}" name="um_ezywayMid" path="um_ezywayMid"/>
					<div class="col s12 m4 l6"><h5 style="color:#005baa">MobileUser UM Ezyway Details Mid: </h5></div>
				<div class="col s12 m4 l6"><h5 style="color:#005baa">${mobileUser.um_ezywayMid }</h5></div>
				
				
					<div class="input-field col s12 m6 l6 ">
						<label for="um_ezywayTid">TID</label>
						<label id="errezywayTid"><c:if test="${resDataum_ezywaytid != null}">
							<H4 style="color: #ff4000;" align="center">${resDataum_ezywaytid}</H4>
						</c:if></label>
						<input type="text" class="form-control" id="um_ezywayTid" value="${mobileUser.um_ezywayTid}"
						placeholder="um_ezywayTid" name="um_ezywayTid"  path="um_ezywayTid"
						onclick="document.getElementById('errezywayTid').style.display='none';"/>									
					 </div>
					
					<div class="input-field col s12 m6 l6 ">					
						<label for="um_ezywaydeviceId">Device Id</label>
						<label id="errezywaydeviceId"><c:if test="${resDataum_ezywaydeviceid != null }">
							<H4 style="color: #ff4000;" align="center">${resDataum_ezywaydeviceid}</H4>
						</c:if></label>
					<input type="text" id="um_ezywaydeviceId" value="${mobileUser.um_ezywaydeviceId}"
						placeholder="um_ezywaydeviceId" name="um_ezywaydeviceId"  path="um_ezywaydeviceId"
						onclick="document.getElementById('errezywaydeviceId').style.display='none';"/>							
					</div>
				
				
				<div class="input-field col s12 m6 l6 ">					
				<select name="um_ezywayrefNo" path="um_ezywayrefNo" value=""
					id="um_ezywayrefNo"  style="width:100%">
					<option selected value=""><c:out value="um_ezywayrefNo" /></option>
					<c:forEach items="${umrefNoList}" var="refNo">
						<option value="${refNo}">${refNo}</option>
					</c:forEach>
				</select>
				<label for="um_ezywayrefNo">Reference No</label>
				</div>

				</div>
				
				<div class="row umezyrec" style="display:none;">
				<input type="hidden" value="${mobileUser.um_ezyrecMid}" name="um_ezyrecMid" path="um_ezyrecMid"/>
					<div class="col s12 m4 l6"><h5 style="color:#005baa">MobileUser UM Ezyrec Details Mid: </h5></div>
				<div class="col s12 m4 l6"><h5 style="color:#005baa">${mobileUser.um_ezyrecMid }</h5></div>
				
				
					<div class="input-field col s12 m6 l6 ">
						<label for="um_ezyrecTid">TID</label>
						<label id="errezyrecTid"><c:if test="${resDataum_ezyrectid != null}">
							<H4 style="color: #ff4000;" align="center">${resDataum_ezyrectid}</H4>
						</c:if></label>
					<input type="text"  id="um_ezyrecTid" value="${mobileUser.um_ezyrecTid}"
						placeholder="um_ezyrecTid" name="um_ezyrecTid"  path="um_ezyrecTid"
						onclick="document.getElementById('errezyrecTid').style.display='none';"/>												
					 </div>
					
					<div class="input-field col s12 m6 l6 ">					
						<label for="um_ezyrecdeviceId">Device Id</label>
						<label id="errezyrecdeviceId"><c:if test="${resDataum_ezyrecdeviceid != null }">
								<H4 style="color: #ff4000;" align="center">${resDataum_ezyrecdeviceid}</H4>
							</c:if></label>
						<input type="text" id="um_ezyrecdeviceId" value="${mobileUser.um_ezyrecdeviceId}"
							placeholder="um_ezyrecdeviceId" name="um_ezyrecdeviceId"  path="um_ezyrecdeviceId"
							onclick="document.getElementById('errezyrecdeviceId').style.display='none';"/>
																
					</div>
				
				
				<div class="input-field col s12 m6 l6 ">					
					
					<select name="um_ezyrecrefNo" path="um_ezyrecrefNo" value=""
						id="um_ezyrecrefNo"  style="width:100%">
						<option selected value=""><c:out value="um_ezyrecrefNo" /></option>
						<c:forEach items="${umrefNoList}" var="refNo">
							<option value="${refNo}">${refNo}</option>
						</c:forEach>
					</select>
					<label for="um_ezyrecrefNo">Reference No</label>
				</div>

				</div>
				
				<div class="row umezypass" style="display:none;">
				<input type="hidden" value="${mobileUser.um_ezypassMid}" name="um_ezypassMid" path="um_ezypassMid"/>
					<div class="col s12 m4 l6"><h5 style="color:#005baa">MobileUser UM Ezypass Details Mid: </h5></div>
				<div class="col s12 m4 l6"><h5 style="color:#005baa">${mobileUser.um_ezypassMid }</h5></div>
				
				
					<div class="input-field col s12 m6 l6 ">
						<label for="um_ezypassTid">TID</label>
						<label id="um_ezypassTid"><c:if test="${resDataum_ezypasstid != null }">
							<H4 style="color: #ff4000;" align="center">${resDataum_ezypasstid}</H4>
						</c:if></label>
					<input type="text" id="um_ezypassTid" value="${mobileUser.um_ezypassTid}"
						placeholder="um_ezypassTid" name="um_ezypassTid"  path="um_ezypassTid"
						onclick="document.getElementById('errezypassTid').style.display='none';"/>
																			
					 </div>
					
					<div class="input-field col s12 m6 l6 ">					
						<label for="um_ezypassdeviceId">Device Id</label>
						<label id="errezypassdeviceId"><c:if test="${resDataum_ezypassdeviceid != null }">
			                                             
							<H4 style="color: #ff4000;" align="center">${resDataum_ezypassdeviceid}</H4>
						</c:if></label>
					<input type="text" id="um_ezypassdeviceId" value="${mobileUser.um_ezypassdeviceId}"
						placeholder="um_ezypassdeviceId" name="um_ezypassdeviceId"  path="um_ezypassdeviceId"
						onclick="document.getElementById('errezypassdeviceId').style.display='none';"/>													
					</div>
				
				
				<div class="input-field col s12 m6 l6 ">										
					<select name="um_ezypassrefNo" path="um_ezypassrefNo"
						id="um_ezypassrefNo" style="width:100%" value="">
						<option selected value=""><c:out value="um_ezypassrefNo" /></option>
						<c:forEach items="${umrefNoList}" var="refNo">
							<option value="${refNo}">${refNo}</option>
						</c:forEach>
					</select>
					<label for="um_ezypassrefNo">Reference No</label>
				</div>

				</div>
				
				
				
				</div>
				<c:if test="${mobileUser.businessName !=null }">
				 <button type="button" class="btn btn-success"
				 onclick="return loadSelectData();" id="submit-btn">Submit </button>
				 <br/>
				 </c:if>
				 
        </div>
      </div>
    </div>
    </div>
    
    </c:if>
   </form>
	
	
	
	</div>
    </body>
	</html>