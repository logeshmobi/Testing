<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<html lang="en-US">
<head>
	<meta charset="UTF-8">
	<meta content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no" name="viewport">
	<meta name="viewport" content="width=device-width, initial-scale=1">

<style>
.td {
	text-align: right;
}

@midea screen(max-width:40%)
</style>


<!-- <script lang="JavaScript">
	$(document).ready(function() {
		$("input").click(function() {
			$("p").hide();
		});
	});

	jQuery(document).ready(function() {
		$('#phcode').select2();
		$('#tids').select2();
	});

	$(document).ready(function() {
		$("select").click(function() {
			$("p").hide();
		});
	});
</script> -->

<script lang="JavaScript">

window.onload = getLocation();

function getLocation() {

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

function phcodeon() {

	var phcode1 = document.getElementById("phcode").value;
	//alert("phcode: "+phcode1);
	document.getElementById("phno").value = phcode1 + " ";
	document.getElementById("phno").focus();
}

$(document).ready(function() {
	$('#phno').blur(function(e) {
		if (!validatePhone('phno')) {
			$('#spnPhoneStatus').html('*<font color=blue>Please specify a valid phone number..!</font>');
			$('#spnPhoneStatus').css('color', 'red');
				} else {
					$('#spnPhoneStatus').html('');
					//document.getElementById("amount").focus();
										}
									});
				});

function validatePhone(txtPhone) {

	var phone_number = document.getElementById("phno").value;
	a = phone_number.replace(/\s+/g, "");
	// alert("length a: "+a.length);
	//var filter = /^\d+$/;
	//var filter = /^[0-9]+$/;
	// var filter = /([0-9]{10})|(\([0-9]{3}\)\s+[0-9]{3}\-[0-9]{4})/;
	var filter = /^((\+[1-9]{1,4}[ \-]*)|(\([0-9]{2,3}\)[ \-]*)|([0-9]{2,4})[ \-]*)*?[0-9]{3,4}?[ \-]*[0-9]{3,4}?$/;
	if (filter.test(a) && a.length > 9) {
		return true;
	} else {
		//document.getElementById("phno").value=' ';
		/*  document.getElementById("phno").focus(); */
		return false;
	}
}

$( document ).ready(function() {
    // bind change event to select
    $('#tids').on('change', function (){
     //alert(this.value);
     var data=this.value.split(",");
     var tid=data[0];
     var expDate1=data[1];
    document.getElementById('tid').value=data[0];
    document.getElementById('expDate1').value=data[1];
    
    if(expDate1==0){
    alert("Your Device has Expired for this username..\nPlease Renewal it");
    return false;
    }
    
    });
  });


function isNumberKey2(evt){
    var charCode = (evt.which) ? evt.which : event.keyCode
    if (charCode > 31 && (charCode < 48 || charCode > 57)){
   	 return false;
    }
    
    $('.creditCardExdt').keyup(function() {
		  var foo = $(this).val().split("-").join(""); // remove hyphens
		  if (foo.length > 0) {
		    foo = foo.match(new RegExp('.{1,2}', 'g')).join("-");
		  }
		  $(this).val(foo);
	});	
    
    $('#cNumber').keyup(function() {
		  var foo = $(this).val().split("-").join(""); // remove hyphens
		  if (foo.length > 0) {
		    foo = foo.match(new RegExp('.{1,4}', 'g')).join("-");
		  }
		  $(this).val(foo);
		});	
    return true;
}

$(document).ready(function() {
	$("#form1").submit(function(event) { 
		
		if (validateForm() && checkExpireDevice()) {
			
			var r = confirm("EZYMOTO Transaction Amount is RM "+document.getElementById("amount1").value
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
	
	
	var contactName = document.getElementById("contactName").value;
	var email = document.getElementById("email").value;
	//var phcode = document.getElementById("phcode").value;
	var phno = document.getElementById("phno").value;
	var receiptVia = document.getElementById("receiptVia").value;
	var tid = document.getElementById("tid").value;
	var referrence = document.getElementById("referrence").value;
	
	var amount = document.getElementById("amount1").value;
	var cardName = document.getElementById("cName").value;
	var cardNo = document.getElementById("cNumber").value;
	var exdt = document.getElementById("exdt1").value;
	var cvv = document.getElementById("cvv").value;
	var hostType = document.getElementById("hostType").value;
	
	
	//alert(contactName+" "+email+"  "+phno+" "+receiptVia+" "+tid+" "+referrence);
	//alert(amount+" "+cardName+" "+cardNo+" "+cvv+" "+exdt+" "+hostType);

	//contactName
	if (contactName == null || contactName == '') {
		alert("Empty Name..Please fill it..");
		document.getElementById("contactName").focus();
		return false;
	}

	//email
	if (email == null || email == '') {
		alert("Empty Email..Please fill it..");
		document.getElementById("email").focus();
		return false;
	}else{
		
		if (!validateEmail(email)) {
		    alert("Enter valid Email...");
			document.getElementById("email").focus();
			return false;
		  } else {
			console.log(" Valid Email");
		  }
	}
	
	//phone
	if (validatePhNumber()) {
			document.getElementById("phno").focus();
			return false;
	} else {
		console.log(" Valid Phone");
	}
	
	//receiptVia
	if (receiptVia == null || receiptVia == '') {
		alert("Select Send Receipt....");
		document.getElementById("receiptVia").focus();
		return false;
	}
	
	//Mobile User
	if (tid == null || tid == '') {
		alert("Select Mobile User....");
		document.getElementById("tid").focus();
		return false;
	}
	
	//Referrence
	if (referrence == null || referrence == '') {
		alert("Empty Referrence..Please fill it..");
		document.getElementById("referrence").focus();
		return false;
	}
	
	
	//Amount
	if (amount == '') {
				alert("Empty Amount..Please fill it..");
				document.getElementById("amount1").focus();
				return false;

	}else{
					
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
					    					  //return true;
					    	
					       					}
					     				   else if(array1[1].length>=1){
					      					 		amount=amount+"0";
			      					          		// alert("Yours Requested Amount to do Transaction "+amount);
			     				          			document.getElementById("amount").value=amount;
			          				       			//return true;
			          					    }
			               					else{
			              						  amount=amount+"00";
			              						   //alert("Yours Requested Amount to do Transaction "+amount);
			              						   document.getElementById("amount").value=amount;
			                						 //return true;
			            				   }
					   
								}
								else{
					    			 amount=amount+".00";
					    			 // alert("Yours Requested Amount to do Transaction "+amount);
					    			  document.getElementById("amount").value=amount;
					    		  //return true;
					       		}
					       }
					 else{
					    	 
					    	  alert("Enter Valid Amount ....");
					    	  document.getElementById("amount1").focus();
					    	   return false;
					       }
				
		}
	
	//Card Name
	if (cardName == '') {
				alert("Empty Card Name..Please fill it..");
				document.getElementById("cName").focus();
				return false;
	}
	
	//Card Number
	if (cardNo == '') {
				alert("Empty Card Number..Please fill it..");
				document.getElementById("cNumber").focus();
				return false;

			}else{
				
				var string = cardNo; // just an example
				var card1 = string.replace(/-/g, ''); '123456'
				
				if (card1.length < 16) {
					alert("Invalid card number");
					document.getElementById("cNumber").focus();
					return false;

				}else{
					var value = card1;
					//function valid_credit_card(cardNo) {
						  // Accept only digits, dashes or spaces
							if (/[^0-9-\s]+/.test(value)){
								alert("Invalid card number");
								document.getElementById("cNumber").focus();
								 return false;
							}

							// The Luhn Algorithm. It's so pretty.
							let nCheck = 0, bEven = false;
							value = value.replace(/\D/g, "");

							for (var n = value.length - 1; n >= 0; n--) {
								var cDigit = value.charAt(n),
									  nDigit = parseInt(cDigit, 10);

								if (bEven && (nDigit *= 2) > 9) nDigit -= 9;

								nCheck += nDigit;
								bEven = !bEven;
							}
							
							if((nCheck % 10) != 0){
								alert("Invalid card number");
								document.getElementById("cNumber").focus();
								return false;
							}

							//return (nCheck % 10) == 0;
						//}
				}
	}
	
	//Expiry Date
	if (exdt == '') {
				alert("Empty Expiry..Please fill it..");
				document.getElementById("exdt1").focus();
				return false;

			}else{
				
				var string = exdt; // just an example
				var exdt1 = string.replace(/-/g, ''); '123456'
				if (exdt1.length < 4) {
					alert("Invalid Expiry date");
					document.getElementById("exdt1").focus();
					return false;

				}else {
					 var d1 = [01,02,03,04,05,06,07,08,09,10,11,12];
					 /* var mm = exdt.substr(exdt.length - 2); */
					 var mm = exdt1.substring(0, 2);
					 var n = d1.includes(Number(mm));
					 
					 if(n == false){
						 alert("Invalid Expiry date");
						 document.getElementById("exdt1").focus();
						 return false;
					 }
					
				}
	}
	
	
	 document.getElementById("expDate").value=exdt;
	
	//CVV
	if (cvv == '') {
		alert("Empty CVV..Please fill it..");
		document.getElementById("cvv").focus();
		return false;

	}else{
		if (cvv.length < 3) {
			alert("Invalid CVV");
			document.getElementById("cvv").focus();
			return false;

			}
	}

	return true;
	
}

function validateEmail(email) {
	  var re = /^(([^<>()[\]\\.,;:\s@\"]+(\.[^<>()[\]\\.,;:\s@\"]+)*)|(\".+\"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/;
	  return re.test(email);
	}
	
function validatePhNumber() {

	var phno = document.getElementById("phno").value;
	var phno1 = phno.replace(/\s/g,'')
	/* console.log("phno -" + phno1); */
	
	if(phno!=null && phno.length!=0)
	{
		if(!phno.includes("+")){
		alert("Invalid Phone No...add country code!! Ex: +60 1233***454");
		document.getElementById("phno").focus();
		return true;
		}
		
		if (phno1.length >= 14)
		{
			alert("Enter valid Phone Number along with Country Code");
			document.getElementById("phno").focus();
			return true;
		}else 
		{
			return false;
		} 

	} 
	else 
	{
		if (phno.trim() == "")
		{
			alert("Empty Phone Number..Please fill it..");
			document.getElementById("phno").focus();
			return true;
		} 
		else 
		{
			return false;
		}
	}

}


//Check Expiry
function checkExpireDevice(){
	
      var expDate1=document.getElementById('expDate1').value;
      if(expDate1==0){
      	  alert("Your Device has Expired for this username..\nPlease Renewal it");
     	  return false;
      }else{
    	  return true;
      }

}

</script>

</head>

<body >

	<form:form method="post" id="form1" name="form1" commandName="motoTxnDet"
		action="${pageContext.request.contextPath}/directDebit/authDDSubmit">

<div class="container-fluid">    
  <div class="row"> 
  <div class="col s12">
      <div class="card blue-bg text-white">
        <div class="card-content">
          <div class="d-flex align-items-center">
           <h3 class="text-white">  <strong>EZYAUTH Payment</strong></h3>
          </div>
          
          
        </div>
      </div>
    </div>
    </div>
		<div class="row">
		<div class="col s12">
      <div class="card border-radius">
        <div class="card-content padding-card">

			<form:input type="hidden" path="motoMid" name="motoMid" id="motoMid" value="${merchant.mid.motoMid }" />
			<form:input type="hidden" id="tid" path="tid" value="" />
			<form:input type="hidden" name="latitude" value="" id="latitude" path="latitude" />
			<form:input type="hidden" name="longitude" value="" id="longitude" path="longitude" />
			<form:input type="hidden" name="expDate" value="" id="expDate" path="expDate" />
			<form:input type="hidden" id="amount" value="" name="amount" path="amount" />
			<form:input type="hidden" id="hostType" value="${merchant.merchantType}" name="hostType" path="hostType" />
			<input type="hidden" id="expDate1" />
			
			<div class="d-flex align-items-center">
					<p>
						<h3 style="color: blue; font-weight: bold;">${requestScope.responseData}</h3>
					</p></div>

					<c:if test="${responseErrorData!=null }">
						<center>
							<p id="responseErrorData">
							<h3 style="color: red;">${responseErrorData}</h3>
							</p>
						</center>
					</c:if>
						
						<div class="row" id="maindiv">
								<div class="input-field col s12 m6 l6 ">
												<label class="control-label" style="">Name</label>
												<form:input class="form-control" id="contactName" value=""
													name="contactName" placeholder="Enter Name"
													path="contactName" />
											</div>
										


									<div class="input-field col s12 m6 l6 ">
												<label class="control-label">Email</label>
												<form:input class="form-control" id="email" value=""
													name="email" placeholder="Enter Email Id" path="email" />
											</div>
										<div class="input-field col s12 m6 l6 ">
												
												<select name="phcode" id="phcode" class="form-control" onchange="return phcodeon();">
													<option selected value="" style="width:;"><c:out
															value="Choose CountryCode" /></option>

													<c:forEach items="${listCountry}" var="lc">
														<option value="${lc.phoneCode}">${lc.countryName}
															${lc.phoneCode}</option>
													</c:forEach>
												</select>
												<label class="control-label" style="width: 10rem;">PhoneCode</label>
												</div>
												<div class="input-field col s12 m6 l6 ">
												<label class="control-label" style="width: 10rem;">PhoneNumber</label>
												<form:input class="form-control" type="tel" value=""
													id="phno" name="phno" placeholder="Enter phone no"
													path="phno" onchange="javascript:phonenumber();" />
												<center>
													<span id="spnPhoneStatus"></span>
												</center>
											</div>
										<div class="input-field col s12 m6 l6 ">

												
												<select onchange="this.value;" name="receiptVia" id="receiptVia"
													class="form-control">
													<option selected value=""><c:out value="Choose Option" /></option>
													<option value="YES" title="">WhatsApp</option>
													<option value="NO" title="">SMS</option>
												</select>
												<label class="control-label">Send Receipt</label> 
											</div>
										<div class="input-field col s12 m6 l6 ">

												<select
													onchange="this.value;" name="tids" id="tids"
													class="form-control">
													<option selected value=""><c:out
															value="Choose Mobile UserName" /></option>

													<c:forEach items="${mobileuser}" var="mobileuser">
														<option
															value="${mobileuser.motoTid},${mobileuser.failedLoginAttempt}"
															title="${mobileuser.motoTid}">${mobileuser.username}</option>
													</c:forEach>
												</select>
												<label class="control-label">Mobile UserName</label> 


											</div>
										<div class="input-field col s12 m6 l6 ">
												<label class="control-label">Reference</label>
												<form:input class="form-control" value="" id="referrence"
													path="referrence" name="referrence" maxlength="30"
													placeholder="Enter reference" />
											</div>
										<div class="input-field col s12 m6 l6 ">
													<label class="control-label">Amount</label>
													<input type="text" class="form-control" id="amount1" value="" maxlength="10"
														name="amount1" path="amount1" placeholder="Enter Amount" />
												</div>
											<div class="input-field col s12 m6 l6 ">
												<label class="control-label">Name on Card</label>
												<form:input class="form-control" id="cName" value=""
													name="cName" path="cName" maxlength="26" placeholder="Enter Card Holder Name" />
											</div>
										<div class="input-field col s12 m6 l6 ">
												<label class="control-label">Card Number</label>
												<form:input class="form-control" value="" id="cNumber"
													path="cNumber" name="cNumber" maxlength="19"
													onclick="return isNumberKey2(event)"
													placeholder="Enter Card Number" />
											</div>
										<div class="input-field col s12 m6 l6 ">
												<label class="control-label">Expiry Month & Year</label>
												<%-- <form:input class="form-control" value="" id="expDate"
													path="expDate" name="expDate" onkeypress="return isNumberKey2(event)"
													onpaste="return false;" name="exdt[]" value="" placeholder="MM-YY" /> --%>
													
													
												<input type="text" class="form-control creditCardExdt"
											id="exdt1" " maxlength="5"
											onkeypress="return isNumberKey2(event)" 
											onpaste="return false;" name="exdt[]" value=""
											placeholder="MM-YY">
													
											</div>
										<div class="input-field col s12 m6 l6 ">
												<label class="control-label">CVV</label>
												<form:input class="form-control" value="" id="cvv" type="password"
													path="cvv" name="cvv" maxlength="3"
													placeholder="Enter CVV" />
											</div>
										</div>
						

								<button class="submitBtn" type="submit"
									>Submit</button>

							</div>
						</div>
					</div>


				</div>
			</div>


	</form:form>

</body>
</html>

