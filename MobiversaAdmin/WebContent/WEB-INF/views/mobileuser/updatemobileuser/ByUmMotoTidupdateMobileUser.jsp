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
$('#referenceNo').change(function(){
  if($(this).val() == $('#ezypassrefNo').val()) {
    alert('Duplicate value');
    return false;
   /*  $(this).val(''); */
  }
}); 
}); 

jQuery(document).ready(function() {
$('#ezypassrefNo').change(function(){
  if($(this).val() == $('#referenceNo').val()) {
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
       { /* 
    	  var businessName=document.getElementById("businessName").value;
          if(businessName == '' || businessName == null){
  		  	 
  			 alert("Please select BusinessName..");
  			  return false;
  			 
  		  }
  		  
         var contactName=document.getElementById("contactName").value;
          if(contactName != '' || contactName != null){
  		  	  if(!allLetterSpace(document.form1.contactName, 2, 100))
  				  
  			  {
  		  		
  			  return false;
  			  } 
  		  } */
  		  
  		var e = document.getElementById("renewalPeriod").value;
	       
	       if (e == null || e == '' ) {
	       
				alert("Please Select Renewal Period");
				 //form.submit = false; 
				return false;
				}    
		    
  		  var expiryDate=document.getElementById("expiryDate").value;
          if(expiryDate == '' || expiryDate == null){
  		  	   alert("please fill Expiry Date");
  			  return false;
  			 
  		  } 
    	 
          var tid = document.getElementById("tid").value;
  		var referenceNo = document.getElementById("referenceNo").value;
  		var deviceId = document.getElementById("deviceId").value;
			//e-moto

			if (tid != '' && tid != null) {
				if (!allnumeric(document.form1.tid, 8, 8)) {
					return false;
				}

				if (referenceNo == null || referenceNo == ''
						|| referenceNo == 'refNo') {

					alert("Please Select Reference No");
					//form.submit = false; 
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
			}

			//ezypass
			var ezypassTid = document.getElementById("ezypassTid").value;
  		var ezypassrefNo = document.getElementById("ezypassrefNo").value;
  		var ezypassdeviceId = document.getElementById("ezypassdeviceId").value;
		
			if (ezypassTid != '' && ezypassTid != null) {
				if (!allnumeric(document.form1.ezypassTid, 8, 8)) {
					return false;
				}

				if (ezypassrefNo == null || ezypassrefNo == ''
						|| ezypassrefNo == 'refNo') {

					alert("Please Select Ezypass Reference No");
					//form.submit = false; 
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

			}

			//ezyrec
	
  		var ezyrecTid = document.getElementById("ezyrecTid").value;
		var ezyrecrefNo = document.getElementById("ezyrecrefNo").value;
		var ezyrecdeviceId = document.getElementById("ezyrecdeviceId").value;
			if (ezyrecTid != '' && ezyrecTid != null) {
				if (!allnumeric(document.form1.ezyrecTid, 8, 8)) {
					return false;
				}

				if (ezyrecrefNo == null || ezyrecrefNo == ''
						|| ezyrecrefNo == 'refNo') {

					alert("Please Select Ezyrec Reference No");
					//form.submit = false; 
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
			}

			

			if (referenceNo == '' && ezypassrefNo == '' && ezyrecrefNo == '') {
				alert("RefNo,Ezyrec RefNo and Ezypass RefNo are Empty.. Please fill it ");
				return false;
			}
			if ((referenceNo == ezypassrefNo) && referenceNo=='' && ezypassrefNo=='') {
				alert("RefNo and Ezypass RefNo are Same.. Please change it as unique ");
				return false;
			}
			if ((ezypassrefNo == ezyrecrefNo) && ezypassrefNo =='' && ezyrecrefNo=='refNo') {
				alert("Ezyrec RefNo and Ezypass RefNo are Same.. Please change it as unique ");
				return false;
			}
			if (ezyrecrefNo == referenceNo && ezyrecrefNo=='' && referenceNo=='') {
				alert("RefNo and Ezyrec RefNo are Same.. Please change it as unique ");
				return false;
			}

			
			var e = document.getElementById("businessName").value;

			if (e == null || e == '') {

				alert("Please Select BusinessName");

				return false;
			}
		/*   var referenceNo = document.getElementById("referenceNo").value; 
		  var ezypassrefNo = document.getElementById("ezypassrefNo").value;
		  var ezyrecrefNo = document.getElementById("ezyrecrefNo").value;
		   
		 if(referenceNo =='' && ezypassrefNo=='' && ezyrecrefNo=='') 
		 {
		 alert("Ezywire RefNo,EzyRec RefNo and Ezypass RefNo are Empty.. Please fill it ");
		 return false;
		 }
		 if(referenceNo == ezypassrefNo) 
		 {
		 alert("Ezywire RefNo and Ezypass RefNo are Same.. Please change it as unique ");
		 return false;
		 }
		if(ezyrecrefNo=='')
		 {
		 	alert("Please select EzyRec RefNo");
		 	 return false;
		 }
		if(ezypassrefNo=='')
		 {
		 	alert("Please select Ezypass RefNo");
		 	 return false;
		 }
		 if(ezypassrefNo=='')
		 {
		 	alert("Please select Ezypass RefNo");
		 	 return false;
		 } */
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
		  
		  alert("END")
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
<body>
<div class="container-fluid">    
<div class="row">  
  <div class="col s12">
      <div class="card blue-bg text-white">
        <div class="card-content">
          <div class="d-flex align-items-center">
           <h3 class="text-white">  <strong> Update Mobile User </strong> </h3>
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
								
									Business Name
									
							</div>	
					
							<div class="input-field col s12 m5 l5">
									<select  name="merchantName" class="browser-default select-filter"
								id="merchantName" path="merchantName">
								<optgroup label="Business Names" style="width:100%">
								<option selected value=""><c:out value="business Name" /></option>
								<c:forEach items="${merchant1}" var="merchant1">
								<c:if test="${merchant1.role == 'BANK_MERCHANT'}">
									<option value="${pageContext.request.contextPath}/mobileUser/findMobileuserDetails?id=${merchant1.id}">
									
									
									<%-- <c:out value="${merchant1.mid.mid}~${merchant1.businessName}~${merchant1.role}"> --%>
									<!-- ${merchant1.id}~${merchant1.businessName}~${merchant1.username}~${merchant1.role} -->
									${merchant1.businessName}~${merchant1.username}~${merchant1.role}<%-- </c:out> --%>
									</option>
</c:if>
								</c:forEach>
								</optgroup>
							</select>
							<%-- <input type="text"  path="businessName" id="businessName"
								name="businessName" Placeholder="businessName" value="${mobileUser.businessName }"/> --%>
							</div></div>
							<div class="row">
					 <div class="input-field col s12 m3 l3"></div>

        <!-- Script -->
        <script>
        $(document).ready(function(){
          
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
.select2-results__options li{
	list-style-type: none;												
	padding: 4px 0px 4px 4px !important;
	font-size: 18px !important;
}
ul.select2-results__options li:hover{
	/* overflow:scroll;   */
}
ul.select2-results__options li{
	max-height:250px;
	/* overflow:auto;   */
	curser:pointer;
 }
li ul .select2-results__option:hover{
	background-color: #005baa !important;
	/* overflow: auto; */
	color:#fff !important; 
}
.select-search-hidden .select2-container{
	display:none !important;
} 

</style>				
						<input  class="shownSearch" type="text"  path="businessName" id="businessName"
								name="businessName" Placeholder="businessName" value="${mobileUser.businessName }"/>		
							
						</div>	</div>
						</div></div></div>
				<style>
.drop-details .select-wrapper input.select-dropdown  { 
    border: 1px solid #005baa;    
     border-radius: 16px; padding: 0 12px; color:#005baa;
}

.drop-details .select-wrapper .caret {
    fill: #005baa;
}
</style> 
						
						
						
				<form method="GET" id="form1" action="${pageContext.request.contextPath}/mobileUser/updateUmMidTxnDetails"
			name="form1" commandName="mobileUser">		
						
					<input type="hidden" name="updateType" value="umobilemoto" path="updateType"/>
				<div class="row">
  <div class="col s12">
      <div class="card border-radius">
        <div class="card-content padding-card">
		 <div class="d-flex align-items-center">
					<h3>MobileUser Details</h3></div>
					
						
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
				<c:if test="${mobileUser.um_motoTid != null}">
					<input type="hidden" value="${mobileUser.um_motoMid}" name="um_motoMid" path="um_motoMid"/>
					
					<div class="row" id="um_motoDiv">
  <div class="col s12">
      <div class="card border-radius">
        <div class="card-content padding-card">
		 <div class="d-flex align-items-center">
						<h3 >MobileUser EzyMoto Details <font color="blue"> Mid: ${mobileUser.um_motoMid}</font> </h3>
</div>
						<div class="row">
						<%-- <c:if test="${mobileUser.um_mototidList !=null}">
							<div class="input-field col s12 m6 l6 ">
									
									<select name="tid2"
								id="tid2" path="tid2">
								
								<option selected value=""><c:out value="MOTO TID" /></option>
								
								<c:forEach items="${mobileUser.um_mototidList}" var="um_motoTid">
									<option <c:out value="${tid}"/>>
									
									<option value="${pageContext.request.contextPath}/mobileUser/findUmMotoTidUserDetails?umMotoTid=${um_motoTid}&umMotoMid=${mobileUser.um_motoMid}">
									
									
									${um_motoTid}
									</option>

								</c:forEach>
								
							</select>
							<label for="tid">Select TID</label>
								 </div>
								 
								 </c:if> --%>
	
							<div class="input-field col s12 m6 l6 ">
									<label for="tid">TID</label>
									<input type="text"  id="motoTid" value="${mobileUser.um_motoTid}"
												placeholder="motoTid" name="motoTid"  path="motoTid" readonly="true"/>
											
									
								 </div>
							<div class="input-field col s12 m6 l6 ">
											<label for="Email">User Name</label>
											<input type="text"  id="motousername" value="${mobileUser.motousername }"
												placeholder="motousername" name="motousername" path="motousername"
												readonly="true"/>
										</div>
										
										
						<%-- 	<div class="input-field col s12 m6 l6 ">
											<label for="hashKey">HashKey</label> <input type="text"
												id="hashkey" value="${mobileUser.hashkey}"
												placeholder="hashkey" name="hashkey" path="hashkey" />
										</div> --%>
										<div class="input-field col s12 m6 l6 ">
											<label for="DTL">DTL</label> <input type="text" id="DTL"
												value="${mobileUser.DTL}" placeholder="DTL" name="DTL"
												path="DTL" />
										</div>
				
						</div>
					</div></div></div></div>
					
					</c:if>
				
					
					
							
							<button class="submitBtn" type="submit"
							onclick="return loadSelectData();">Submit</button>
							
	</form>
						</div>
					
				
		


	</body>
	</html>
	
