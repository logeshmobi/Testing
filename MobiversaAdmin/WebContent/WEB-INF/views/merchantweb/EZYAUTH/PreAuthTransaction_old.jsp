
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
<!-- <script type="text/javascript" src="https://ajax.googleapis.com/ajax/libs/jquery/3.1.0/jquery.min.js"></script> -->
<script type="text/javascript"
	src="${pageContext.request.contextPath}/resourcesNew/js/urlHide.js"></script>
<!-- <script src="alert/dist/sweetalert.min.js"></script>
  <link rel="stylesheet" href="alert/dist/sweetalert.css"> -->

<script type="text/javascript"
	src="${pageContext.request.contextPath}/resourcesNew/js/plugins/select2.min.js"></script>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/resourcesNew/js/jquery-2.1.4.min.js"></script>


 <meta charset="utf-8">
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
  <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
  <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
<style>
.td {
	text-align: right;
}

@midea screen(max-width:40%)
</style>


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
			$("p").hide();
		});
	});
</script>
<script type="text/javascript">
	jQuery(document).ready(function() {

		$('#phcode').select2();

		$('#tid').select2();

	});
</script>
<script lang="JavaScript">
	$(document).ready(function() {
		$("select").click(function() {
			$("p").hide();
		});
	});
</script>
<script lang="JavaScript">
	$(document).ready(function() {
		$("input").click(function() {
			$("p").hide();
		});
	});
</script>

  <!-- <script type="text/javascript">
  function loadmsg()
  {
 
 alert("loadmsg");
    alert(${responseMessage});
     alert(${responseData1});
   if(${responseMessage == 'SUCCESS'})
	   {
	   //alert("total deviceCount loaddashboard(): "+${deviceCount});
	   // alert("total devicecheck loaddashboard(): "+${checkDeviceStatus});
	   
	   
	   ///alert("total deviceCount loaddashboard(): "+${deviceCount});
	   swal({
				title : "${responseData1} ",
				text : "Please Check Your Readers List.",
				type : "warning",
				
				confirmButtonColor: "#DD6B55",
				confirmButtonClass: "btn-danger",
				confirmButtonText : "Okay!",
				
				closeOnConfirm : false,
		
			}
			
			);
	   
	   
	   };
   
  }
 
  </script> -->
	
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

<!-- <script type="text/javascript">
function JSalert(){
	swal({   title: "Yours Requested Amount Of MOTO Transaction RM "+document.getElementById("amount").value,   
    text: "Are you sure to proceed?",   
    type: "warning",   
    showCancelButton: true,   
    confirmButtonColor: "#DD6B55",   
    confirmButtonText: "Yes, I am!",   
    cancelButtonText: "No, I am not sure!",   
    closeOnConfirm: true,   
    closeOnCancel: true }  /* , 
    function(isConfirm){   
        if (isConfirm) 
    {   
        //swal("Account Removed!", "Your account is removed permanently!", "success");   
        return true;
        } 
        else {     
           // swal("Hurray", "Account is not removed!", "error");  
           return false; 
            }  }  */);
}
</script> -->

<script lang="JavaScript">

	 $(document).ready(function() {
		$("#form1").submit(function(event) { 
			//alert("validate form");
			//alert(validateForm());
		/* 	function validateForm1(){ */
			if (validateForm() && SubmitFormAmount()) {
			 
				
			 var r = confirm("EZYAUTH Transaction Amount is RM "+document.getElementById("amount").value
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
 
 <form:form method="post" id="form1" name="form1" commandName="preAuthTxnDet"
		action="${pageContext.request.contextPath}/merchantpreauth/preAuthSubmit" >
	
	<%-- <form method="post" id="form1" name="form1" 
		action="${pageContext.request.contextPath}/transactionMoto/motoSubmitTransaction"> --%>
			
	
		<div class="row" >




			<form:input type="hidden" path="motoMid" name="motoMid" id="motoMid" value="${merchant.mid.motoMid }"/>
			<div style="overflow: auto; border: 1px; width: 100%;">

				<div class="content-wrapper" >
					<p><h3 style="color: blue;font-weight: bold;">${requestScope.responseData}</h3></p>
					<!-- start  -->
					<%-- <span id="err" style="color: red;"> <c:if
							test="${requestScope.responseSuccess == 'SUCCESS'}">

							<center>
								<div id="myModal" class="modal" style="vertical-align: middle;">
									<div class="modal-content"
										style="width: 40% ! important; height: 40% ! important; margin-top: 10px; 
										background-color: #00a0bc;">
										<br>
										<br>
										<div class="modal-header">
											<!--  <span class="close">&times;</span> -->
											<p id="msg" name="msg"
												style="color: #1B0AEA; font-size: 20px; color: white;">
												<b>${requestScope.responseData}</b>
											</p>
											<br />
											<center>
												<button type="button" class="btn btn-default"
													data-dismiss="modal" id="myBtn"
													style="border-radius: 15px; width: 168px ! important; height: 40px; 
													text-align: center; border-color: white; border-style: groove; 
													background-color: #00a0bc;">
													<b><a href=""
														onclick="window.open('${requestScope.responseData1}','_new')"
														style="color: white; text-decoration: none;">Confirm
															Payment</a></b>
												</button>
											</center>

											<br />


										</div>
										<br>
										<center>
											<button type="button" class="btn btn-default"
												data-dismiss="modal" id="myBtn">
												<span class="close">Close</span>
											</button>
										</center>
									</div>
								</div>
							</center>

							<script>
 						
 						var modal = document.getElementById('myModal');
	 					var btn = document.getElementById("myBtn");
	 					var span = document.getElementsByClassName("close")[0];
 						modal.style.display = "block";
						span.onclick = function() {
					    modal.style.display = "none";
						}
						btn.onclick = function() {
					    modal.style.display = "none";
						}
						window.onclick = function(event) {
 						   if (event.target == modal) {
 					       modal.style.display = "none";
   							 }
						}
						
					</script>

						</c:if>
					</span> --%>


					<!-- end -->
					
					<h3 class="card-title" style="color:blue;">EZYAUTH Transaction</h3>
					<div class="row" >
						<div class="col-md-12 formContianer" >
							<div class="card" id="maindiv" style="width:auto ! important;">

								<div class="card-body" style="width:100%;">





									<div class="row" style="width:100%;">

										<div class="form-group col-md-4" style="width:100%;" >
											<div class="form-group" >
												<label class="control-label" style="">Name</label>
												<form:input class="form-control" id="contactName" value=""
													name="contactName" placeholder="Enter name" path="contactName"/>


											</div>
										</div>
										</div>
										<div class="row" style="width:100%;">

										<div class="form-group col-md-4" style="width:100%;">
											<div class="form-group">

												<label class="control-label" style="width: 10rem;">Phone
													Number</label><br>
													 <select name="phcode" id="phcode" 
													class="form-control" onchange="return phcodeon();">
													<option selected value="" style="width:;"><c:out
															value="Choose CountryCode" /></option>

													<c:forEach items="${listCountry}" var="lc">
														<option value="${lc.phoneCode}">${lc.countryName}
															${lc.phoneCode}</option>
													</c:forEach>
												</select> <form:input class="form-control" type="tel" value="" id="phno"
													name="phno" placeholder="Enter phone no"  path="phno"
													onchange="javascript:phonenumber();" />
												<center>
													<span id="spnPhoneStatus"></span>
												</center>


											</div>
										</div>

						

									</div>
									
									
									
									<div class="row" style="width:100%;">

										<div class="form-group col-md-4" style="width:100%;" >
											<div class="form-group">
												<label class="control-label">Email</label> <form:input
													class="form-control" id="email" value="" name="email"
													placeholder="Enter Email Id"  path="email" />
											</div>
										</div>

									</div>
										<div class="row" style="width:100%;">

										<div class="form-group col-md-4" style="width:100%;" >
											<div class="form-group">
												<label class="control-label">Amount</label>
												 <form:input
													class="form-control" id="amount" value="" name="amount"  path="amount"
													placeholder="Enter amount" />
													<!-- onchange="return ValidateAmount();" -->
											</div>
										</div>

									</div>
									
									<div class="row" style="width:100%;">
										<div class="form-group col-md-4 i" style="width:100%;" >
											<div class="form-group">
												<label class="control-label">Reference</label> <form:input
													class="form-control" value="" id="referrence" path="referrence"
													name="referrence" placeholder="Enter reference" />

											</div>
										</div>

</div>
										<%-- <div class="row" style="width:100%;">
										<div class="form-group col-md-4 i" style="width:100%;" >
											<div class="form-group">

												<label class="control-label">Delivery Date / CheckIn
													Date</label> <form:input type="text" class="form-control"
													id="datepicker" name="expectedDate" value=""
													placeholder="dd/mm/yyyy" data-toggle="tooltip"
													data-placement="right" path="expectedDate"
													title="Enter date properly, Empty fields should not allowed..!" />
												<!--  -->
												<!-- onsubmit="return validateDateOfBirth(document.getElementById('datepicker'));" >-->
												<span class="error1" id="error1"
													style="color: blue; font-weight: bold; display: none;">
													<font color="red">*</font>Empty Fields not Allowed..!
												</span> <span class="error2" id="error2"
													style="color: blue; font-weight: bold; display: none;">
													<font color="red">*</font>You cannot enter a date in the
													future!.
												</span>


											</div>
										</div>
									</div> --%>

										<div class="row" style="width:100%;">
										<div class="form-group col-md-4 i" style="width:100%;" >
											<div class="form-group">

												<label class="control-label">Mobile UserName</label> 
												<form:select path="tid"
													name="tid" id="tid" class="form-control">
													<option selected value=""><c:out
															value="Choose Mobile UserName" /></option>

													<c:forEach items="${mobileuser}" var="mobileuser">
														<option value="${mobileuser.motoTid}" title="${mobileuser.motoTid}">${mobileuser.username}</option>
													</c:forEach>
												</form:select>


											</div>
										</div>

										<!-- <div class="form-group col-md-4 i">
										<div class="form-group">

											<button class="btn btn-primary" type="button"
												onclick="return loadSelectData();">Search</button>

											<button class="btn btn-primary" type="button"
												onclick="return loaddata();" target="_blank">Export</button>
										</div>
									</div> -->
									</div>

								</div>
							</div>
						</div>

					</div>

					<div class="row">
						<div class="form-group col-md-4 i">
							<div class="form-group">

								<form:input type="hidden" name="latitude" value="" id="latitude" path="latitude"/>
                				<form:input type="hidden" name="longitude" value="" id="longitude" path="longitude"/>
								<button class="submitBtn" type="submit" onclick="return submitForm1();">Submit</button>


							</div>
						</div>
					</div>
					

				</div>
			</div>




			
			</div>
	
	</form:form>
</body>




</html>

