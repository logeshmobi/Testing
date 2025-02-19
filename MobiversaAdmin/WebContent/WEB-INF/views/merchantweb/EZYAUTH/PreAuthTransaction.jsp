
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>
	<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<html lang="en-US">
<head>
<meta charset="UTF-8">
<meta
	content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no"
	name="viewport">

 <meta charset="utf-8">
  <meta name="viewport" content="width=device-width, initial-scale=1">
  
<style>
.td {
	text-align: right;
}

@midea screen(max-width:40%)
</style>
 <script>
  $( document ).ready(function() {
      // bind change event to select
      $('#tids').on('change', function (){
      // alert(this.value);
       var data=this.value.split(",");
       var tid=data[0];
       var expDate=data[1];
      document.getElementById('tid').value=data[0];
      document.getElementById('expDate').value=data[1];
      
      if(expDate==0){
      alert("Your Device has Expired for this username..\nPlease Renewal it");
      return false;
      }
      
      });
    });
</script>



 <script type="text/javascript">
 


	window.onload = getLocation();
 
 
 function getLocation() {
 /* var latitudeAndLongitude=document.getElementById("latitudeAndLongitude"),
location={
    latitude:'',
    longitude:''
};
 */
 
// alert("getlocation.");
if (navigator.geolocation){
  navigator.geolocation.getCurrentPosition(showPosition);
}
else{
  latitudeAndLongitude.innerHTML="Geolocation is not supported by this browser.";
}
}

function showPosition(position){ 
    location.latitude=position.coords.latitude;
    location.longitude=position.coords.longitude;
    document.getElementById("latitude").value=position.coords.latitude;
    document.getElementById("longitude").value=position.coords.longitude;
   /*  latitudeAndLongitude.innerHTML="Latitude: " + position.coords.latitude + 
    "<br>Longitude: " + position.coords.longitude;  */
    var geocoder = new google.maps.Geocoder();
    var latLng = new google.maps.LatLng(location.latitude, location.longitude);

 if (geocoder) {
    geocoder.geocode({ 'latLng': latLng}, function (results, status) {
       if (status == google.maps.GeocoderStatus.OK) {
         console.log(results[0].formatted_address); 
         $('#address').html('Address:'+results[0].formatted_address);
       }
       else {
        $('#address').html('Geocoding failed: '+status);
        console.log("Geocoding failed: " + status);
       }
    }); //geocoder.geocode()
  }      
}
</script>

<script lang="JavaScript">
	$(document).ready(function() {
		$("input").click(function() {
			$("#hide").hide();
		});
	});
</script>
<!-- <script type="text/javascript">
	jQuery(document).ready(function() {

		$('#phcode').select2();

		$('#tids').select2();

	});
</script> -->
<script lang="JavaScript">
	$(document).ready(function() {
		$("select").click(function() {
			$("#hide").hide();
		});
	});
</script>
<script lang="JavaScript">
	$(document).ready(function() {
		$("input").click(function() {
			$("#hide").hide();
		});
	});
</script>

</script>



<script lang="JavaScript">
	function validatePhNumber() {

		var phno = document.getElementById("phno").value;
		var n = phno.search("+");
		if (n >= 1) {
			if (phno.length > 9) {
				return true;
			}
			return false;

		} else {
			/* document.getElementById("phno").focus(); */
			return false;

		}

	}
</script>
<script lang="JavaScript">
	function phcodeon() {

		var phcode1 = document.getElementById("phcode").value;
		//alert("phcode: "+phcode1);
		document.getElementById("phno").value = phcode1 + " ";
		document.getElementById("phno").focus();
	}
	
	
	function mobileuserName(){
		var tids = document.getElementById("tids").value;
		//alert("phcode: "+phcode1);
		document.getElementById("mobileuserName").value = tids;
		document.getElementById("mobileuserName").focus();
	
	}
</script>
<script lang="JavaScript">
	$(document).ready(function() {
		$('#phno').blur(function(e) {
			if (!validatePhone('phno')) {
				$('#spnPhoneStatus').html('*<font color=blue>Please specify a valid phone number..!</font>');
				$('#spnPhoneStatus').css('color', 'red');
					} else {
						$('#spnPhoneStatus').html('');
						document.getElementById("amount").focus();
												//$('#spnPhoneStatus').css('color', 'green'); 
												/* var str1 = document.getElementById("phno1").value;
												 var str2 = document.getElementById("phno2").value;
												 var str=str1+str2;
												str = str1.replace (/,/g, "");
												document.getElementById("phno1").value=str1;
												alert("phno: "+document.getElementById("phno").value); */
											}
										});
					});

	function validatePhone(txtPhone) {

		var phone_number = document.getElementById("phno").value;
		a = phone_number.replace(/\s+/g, "");
		var filter = /^((\+[1-9]{1,4}[ \-]*)|(\([0-9]{2,3}\)[ \-]*)|([0-9]{2,4})[ \-]*)*?[0-9]{3,4}?[ \-]*[0-9]{3,4}?$/;
		if (filter.test(a) && a.length > 9) {
			return true;
		} else {
			//document.getElementById("phno").value=' ';
			/*  document.getElementById("phno").focus(); */
			return false;
		}
	}
</script>
<script lang="JavaScript">
	$(document).ready(function() {

		$("#amount").change(function() {
		
			ValidateAmount();
		});

	});
	
	
	function ValidateAmount(){
	var amount=document.getElementById("amount").value;
		
		
			if(amount.includes(",")){
			
			amount=amount.replace(/,/g , "");
			
			}
			if(!isNaN(amount)){
		if(amount.includes(".")){
		
			var array1 = [];
		    array1= amount.split(".");
		    //alert("array1[1]: "+array1[1]);
		    if(array1[0]=='' || array1[0]==null){
		    	array1[0]="0";
		    }
		    if(array1[1].length>=2){
		    	amount=array1[0]+"."+array1[1].substring(0,2);
		    	//alert("Yours Requested Amount to do Transaction "+amount);
		    	document.getElementById("amount").value=amount;
		    	return true;
		    	
		       }
		       else if(array1[1].length>=1){
		       amount=amount+"0";
                 //alert("Yours Requested Amount to do Transaction "+amount);
                 document.getElementById("amount").value=amount;
                 return true;
               }
               else{
                amount=amount+"00";
                // alert("Yours Requested Amount to do Transaction "+amount);
                 document.getElementById("amount").value=amount;
                 return true;
               }
		       
		    
			
		   
		}
		else{
		    	 amount=amount+".00";
		    	 // alert("Yours Requested Amount to do Transaction "+amount);
		    	  document.getElementById("amount").value=amount;
		    	  return true;
		       }
		       }
		 else{
		    	 
		    	  alert("Enter Proper Amount.");
		    	  document.getElementById("amount").focus();
		    	   return false;
		       }
	
	
	}
</script>
<script lang="JavaScript">
	
	function SubmitFormAmount(){
	var amount=document.getElementById("amount").value;
		
		
			if(amount.includes(",")){
			
			amount=amount.replace(/,/g , "");
			
			}
			if(!isNaN(amount)){
		if(amount.includes(".")){
		
			var array1 = [];
		    array1= amount.split(".");
		    //alert("array1[1]: "+array1[1]);
		    if(array1[1].length>=2){
		    	amount=array1[0]+"."+array1[1].substring(0,2);
		    	//alert("Yours Requested Amount to do Transaction "+amount);
		    	document.getElementById("amount").value=amount;
		    	return true;
		    	
		       }
		       else if(array1[1].length>=1){
		       amount=amount+"0";
                // alert("Yours Requested Amount to do Transaction "+amount);
                 document.getElementById("amount").value=amount;
                 return true;
               }
               else{
                amount=amount+"00";
                 //alert("Yours Requested Amount to do Transaction "+amount);
                 document.getElementById("amount").value=amount;
                 return true;
               }
		       
		    
			
		   
		}
		else{
		    	 amount=amount+".00";
		    	 // alert("Yours Requested Amount to do Transaction "+amount);
		    	  document.getElementById("amount").value=amount;
		    	  return true;
		       }
		       }
		 else{
		    	 
		    	  alert("Enter Proper Amount.");
		    	  document.getElementById("amount").focus();
		    	   return false;
		       }
	
	
	}
</script>


<script lang="JavaScript">

	 $(document).ready(function() {
		$("#form1").submit(function(event) { 
			//alert("validate form");
			
			var amount=document.getElementById("amount").value;
			
			if(amount<5){
				 alert("Enter Amount greater than 5RM.");
		    	  document.getElementById("amount").focus();
		    	   return false;
				
			}
			
			
			if (validateForm() && SubmitFormAmount()
			&& checkExpireDevice()) {
			 var r = confirm("Your Requested Amount Of EZYAUTH Transaction RM "+document.getElementById("amount").value
				+" \nDo You Want to Submit the Transaction..?");
				//JSalert();
					//alert("confirmed transaction: "+r);
				 if (r == true) {
				
					return true;
					//$("form").submit();
				}  else {
					return false;
				}  


				

			} else {
				return false;
			} 

		  });
	});  
	
	//}

	function validateForm() {
		var amount = document.getElementById("amount").value;
		var contactName = document.getElementById("contactName").value;
		var phcode = document.getElementById("phcode").value;
		var phno = document.getElementById("phno").value;
		var tid = document.getElementById("tid").value;
		var referrence = document.getElementById("referrence").value;
		//alert(amount+" "+contactName+" "+phcode+" "+phno+" "+tid+" "+referrence);

		//contactName
		if (contactName == null || contactName == '') {
			alert("Empty Name..Please fill it..");
			document.getElementById("contactName").focus();
			// $( "span" ).text( "Name field is empty..Please fill it..." ).show();
			return false;
		}

		//amount
		else if (amount == null || amount == '') {
			alert("Empty Amount..Please fill it..");
			document.getElementById("amount").focus();
			// $( "span" ).text( "Amount field is empty..Please fill it..." ).show();
			return false;
		}

		//phcode
		/* else if (phcode == null || phcode == '') {
			alert("Empty Phcode..Please Select  it..");
			document.getElementById("phcode").focus();
			// $( "span" ).text( "Phcode field is empty..Please fill it..." ).show();
			return false;
		}

		//phoneno
		else if (phno == null || phno == '') {
			alert("Empty Phone number..Please fill it..");
			document.getElementById("phno").focus();
			// $( "span" ).text( "Phone number field is empty..Please fill it..." ).show();
			return false;
		} */

		//referrence
		else if (referrence == null || referrence == '') {
			alert("Empty Reference field..Please fill it..");
			document.getElementById("referrence").focus();
			// $( "span" ).text( "Refference field is empty..Please fill it..." ).show();
			return false;
		}

		//deviceid
		else if (tid == null || tid == '') {
			alert("Empty DeviceId..Please Select it..");
			document.getElementById("tid").focus();
			//$( "span" ).text( "DeviceId field is empty..Please fill it..." ).show();
			return false;
		}

		else {
			return true;
		}

	}
</script>
</head>

<body >

<div class="container-fluid">    
  <div class="row">
  
 
  
  <div class="col s12">
      <div class="card blue-bg text-white">
        <div class="card-content">
          <div class="d-flex align-items-center">
           <h3 class="text-white">  <strong> EZYAUTH Transaction  </strong></h3>
          </div>
          
          
        </div>
      </div>
    </div>
    </div>
 
 <form:form method="post" id="form1" name="form1" commandName="preAuthTxnDet"
		action="${pageContext.request.contextPath}/merchantpreauth/preAuthSubmit" >

			<form:input type="hidden" path="motoMid" name="motoMid" id="motoMid" value="${merchant.mid.motoMid }"/>
			<div class="row" >
			 <div class="col s12">
      <div class="card border-radius">
        <div class="card-content padding-card">

				<div class="d-flex align-items-center">
					<p><h3 style="color: blue;font-weight: bold;">${requestScope.responseData}</h3></p>
					</div>
					
					<div class="row">
	
										<div class="input-field col s12 m6 l6">
												<label >Name</label>
												<form:input  id="contactName" value=""
													name="contactName" placeholder="Enter name" path="contactName"/>


											</div>
										
										<div class="input-field col s12 m6 l6">
												<label >Email</label> <form:input
													id="email" value="" name="email"
													placeholder="Enter Email Id"  path="email" />
											</div>
										

										<div class="input-field col s12 m6 l6">

												
													 <select name="phcode" id="phcode"  style="width:100%;"
													 onchange="return phcodeon();">
													<option selected value="" ><c:out
															value="Choose CountryCode" /></option>

													<c:forEach items="${listCountry}" var="lc">
														<option value="${lc.phoneCode}">${lc.countryName}
															${lc.phoneCode}</option>
													</c:forEach>
												</select>
												<label >Country Code</label> 
											</div>
											<div class="input-field col s12 m6 l6">
												<form:input class="form-control" type="tel" value="" id="phno"
													name="phno" placeholder="Enter phone no"  path="phno"
													onchange="javascript:phonenumber();" />
												<label >Phone Number</label> 
												<center>
													<span id="spnPhoneStatus"></span>
												</center>


											</div>
											<input type="hidden" id="expDate" />
										</div>
								



								
								<div class="row" >

										<div class="input-field col s12 m6 l6">
												<label >Amount</label>
												 <form:input
													id="amount" value="" name="amount"  path="amount"
													placeholder="Enter amount" />
													<!-- onchange="return ValidateAmount();" -->
											</div>
										
										<div class="input-field col s12 m6 l6">
												<label >Reference</label> <form:input
													value="" id="referrence" path="referrence"
													name="referrence" placeholder="Enter reference" />

											</div>
										
										<div class="input-field col s12 m6 l6">

												
												<select  onchange="this.value;"
													name="tids" id="tids">
													<option selected value=""><c:out
															value="Choose Mobile User" /></option>

													<c:forEach items="${mobileuser}" var="mobileuser">
														<option value="${mobileuser.motoTid},${mobileuser.failedLoginAttempt}"
														 title="${mobileuser.motoTid}">${mobileuser.username}</option>
													</c:forEach>
												</select>
												
												<label >Mobile UserName</label> 
											
											</div>
										</div>

									
									

					
							
							<input type="hidden" name="expDate" value="" id="expDate" />

								<form:input type="hidden" name="tid" value="" id="tid" path="tid"/>
								<form:input type="hidden" name="latitude" value="" id="latitude" path="latitude"/>
                				<form:input type="hidden" name="longitude" value="" id="longitude" path="longitude"/>
								<button class="submitBtn" type="submit" onclick="return submitForm1();">Submit</button>


							
					</div>
					

				</div>
</div></div></form:form>
</div>
</body>




</html>

