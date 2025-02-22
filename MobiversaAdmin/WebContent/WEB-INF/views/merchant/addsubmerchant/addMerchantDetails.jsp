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
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>Add Merchant</title>

<link
	href="https://cdnjs.cloudflare.com/ajax/libs/sweetalert/1.1.3/sweetalert.min.css"
	rel="stylesheet" />
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/2.1.1/jquery.min.js"></script>
<script
	src="https://cdnjs.cloudflare.com/ajax/libs/sweetalert/1.1.3/sweetalert.min.js"></script>

<style>
.submitBtn {
	width: 140px;
	height: 55px;
	border-radius: 49px;
	box-shadow: 0 3px 6px 0 rgba(15, 88, 22, 0.55);
	background-color: #53b749;
	text-align: center;
	margin-bottom: 12px;
	margin-left: 60px;
}

.submitBtn span {
	width: 98px;
	height: 35px;
	text-shadow: 0 3px 6px rgba(0, 0, 0, 0.34);
	font-family: Helvetica;
	font-size: 15px;
	font-weight: bold;
	font-stretch: normal;
	font-style: normal;
	line-height: 1.21;
	letter-spacing: normal;
	text-align: center;
	color: #ffffff;
	display: inline-block;
	margin-top: 18px;
}
</style>
<script type="text/javascript">
	function changeStyle(id) {
		//alert("changeing --"+id.value+"--");
		var field = id.value;
		//alert(" changeing --"+field.length+"--");
		//document.getElementById("mid").focus();
		if (field.length != 0) {
			//alert(" changeing --"+field.length+"--");
			id.style.border = "1px solid #3FCADB";
		} else {
			id.style.border = "1px solid #B5B9B9";
		}
	}

	function changeErrorStyle(id) {

		var field = document.getElementById(id);
		field.style.border = "1px solid #FB2002";
		field.focus();
		return false;

	}
</script>






<!-- To Check the Merchant Type  -->

<script lang="JavaScript">
/* function myFunction(){
	
	var mail = document.getElementById("email").value;
	//alert("myFunction ::::::"+mail);
	
	$.ajax({
        //type: "POST",
      //  url: "admin/emailCheck",
        //data: loginData,
        async: false,
        type : "GET",
    	url : "${pageContext.request.contextPath}/admin/emailCheck",
   		data : {
    		"email" : mail
   		},
        
        success: function (result) {
        	//alert(result);
        	if(result.match('NotExist')) {
        		//alert('Email Not Exist');
        		document.getElementById("emailChck").value='No';
        	}else if(result.match('Exist')) {
        		alert('Email Already Exist');
        		document.getElementById("emailChck").value='Yes';
        	}
        },
        error: function (result) {
            // do something.
        }
    });
	
} */

/* function checkEmail(email) {
	
	var mail = email;
	
	alert("mail111111"+mail);

	 $.ajax({
        //type: "POST",
      //  url: "admin/emailCheck",
        //data: loginData,
        async: false,
        type : "GET",
    	url : "${pageContext.request.contextPath}/admin/emailCheck",
   		data : {
    		"email" : mail
   		},
        
        success: function (result) {
        	alert(result);
        	if(result.match('NotExist')) {
        		alert('Email Not Exist');
        		return true;
        	}else if(result.match('Exist')) {
        		alert('Email Already Exist');
        		return false;
        	}
        },
        error: function (result) {
            // do something.
        }
    });
	
}
 */

	function loadSelectData() {
		
		/* var mail =  document.form1.email.value;
		
		alert("mail::::::"+checkEmail(mail));
		
		if(!checkEmail(mail)){
			alert('bye');
			return false;
		}else if(checkEmail(mail)) {
			alert('hello');
		} */
		
	
		

		if (!allLetterSpaceSpecialCharacter(document.form1.website, 3, 100)) {
			changeErrorStyle("website");
			//document.getElementById("businessName").focus();

			return false;
		}

		

		if (!allLetterSpaceSpecialCharacter(document.form1.businessName, 3, 100)) {
			changeErrorStyle("businessName");
			//document.getElementById("businessName").focus();

			return false;
		}

		  if (!allLetterSpaceSpecialCharacter1(document.form1.email, 1, 30)) {
	            changeErrorStyle("email");
	            //document.getElementById("businessName").focus();

	            return false;
	        }
		
	}

	function phonenumber(inputtxt) {
		var phoneno = /^\d{10}$/;
		if (inputtxt.value.match(phoneno)) {
			return true;
		} else {
			alert("Not a valid Phone Number");
			return false;
		}
	}

	function postcode(inputtxt) {
		/* alert(len1);
		int len =len1; */
		var postcode = /^\d{5}$/;
		if (inputtxt.value.match(postcode)) {
			return true;
		} else if (inputtxt.value.length == 0) {
			alert("Please enter " + inputtxt.name);
			return false;
		} else {
			alert("Not a valid " + inputtxt.name);
			return false;
		}
	}

	// This function will validate Name.  
	function allLetter(inputtxt, minlength, maxlength) {

		var field = inputtxt.value;
		var mnlen = minlength;
		var mxlen = maxlength;
		// var uname = document.registration.username;  
		var letters = /^[A-Za-z- ]+$/;
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
			alert(inputtxt.name + ' must have alphabet characters only');
			//uname.focus();  
			return false;
		}
	}

	// This function will validate Name.  
	function allLetterSpace(inputtxt, minlength, maxlength) {

		var field = inputtxt.value;
		var mnlen = minlength;
		var mxlen = maxlength;
		// var uname = document.registration.username;  
		var letters = /^[a-zA-Z0-9 ]*$/;/*  /^[A-Za-z]+$/; */
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

	function allLetterSpaceSpecialCharacter(inputtxt, minlength, maxlength) {
		//alert("TEste :"+ inputtxt);
		var field = inputtxt.value;
		var mnlen = minlength;
		var mxlen = maxlength;
		// var uname = document.registration.username;  
		var letters = /^[ A-Za-z_()./&-@]*$/; /* /^[a-zA-Z0-9- ]*$/; *//*  /^[A-Za-z]+$/; */
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
			alert(inputtxt.name
					+ ' must have alphanumeric and space and special characters with -,&,/,() only');
			//uname.focus();  
			return false;
		}
	}
	
	function allLetterSpaceSpecialCharacter1(inputtxt, minlength, maxlength) {
        //alert("TEste :"+ inputtxt);
        var field = inputtxt.value;
        var mnlen = minlength;
        var mxlen = maxlength;
        // var uname = document.registration.username;  
        var letters = /^[ A-Za-z_()./&-@]*$/; /* /^[a-zA-Z0-9- ]*$/; *//*  /^[A-Za-z]+$/; */
     /*    if ((field.length == 0) || (field.length < mnlen)
                || (field.length > mxlen)) {
            alert("Please enter the email address");
            return false;
        } else */
        	
        	if (field.match(letters)) {
            // Focus goes to next field i.e. Address.  
            //document.form1.address.focus();  
            return true;
        } else {
            alert(inputtxt.name
                    + ' must have alphanumeric and space and special characters with -,&,/,() only');
            //uname.focus();  
            return false;
        }
    }
	// This function will validate Address.  
	function alphanumeric(inputtxt, minlength, maxlength) {
		var field = inputtxt.value;
		var mnlen = minlength;
		var mxlen = maxlength;
		// var uadd = document.registration.address;  
		var letters = /^[0-9a-zA-Z-]+$/;
		if ((field.length < mnlen) || (field.length > mxlen)) {
			alert("Please input the " + inputtxt.name + " between " + mnlen
					+ " and " + mxlen + " characters");
			return false;
		} else if (field.match(letters)) {
			// Focus goes to next field i.e. Country.  
			//document.registration.country.focus();  
			return true;
		} else {
			alert(inputtxt.name
					+ ' must have alphanumeric with - characters only');
			// uadd.focus();  
			return false;
		}
	}

	// This function will validate ZIP Code.  
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

	// This function will validate Email.  
	function validateEmail(inputtxt) {
		//alert(inputtxt);
		var field = inputtxt.value;
		//var uemail = document.registration.email;  
		var mailformat = /^\w+([\.-]?\w+)*@\w+([\.-]?\w+)*(\.\w{2,5})+$/;
		if (field.match(mailformat)) {
			//document.registration.desc.focus();  
			return true;

		} else if (field.length == 0) {
			alert("You have entered  " + inputtxt.name + " address!");
			//uemail.focus();  
			return false;
		} else {
			alert("You have entered an invalid " + inputtxt.name + " address!");
		}
	}

	function stringlength(inputtxt, minlength, maxlength) {
		var field = inputtxt.value;
		var mnlen = minlength;
		var mxlen = maxlength;

		if ((field.length < mnlen) || (field.length > mxlen)) {
			alert("Please input the " + inputtxt.name + " between " + mnlen
					+ " and " + mxlen + " characters");
			return false;
		} else {
			//alert('Your userid have accepted.');  
			return true;
		}

	}
</script>
</head>
<body>

	<div class="container-fluid">
		<div class="row">
			<div class="col s12">
				<div class="card blue-bg text-white">
					<div class="card-content">
						<div class="d-flex align-items-center">
							<h3 class="text-white">
								<strong> Add Merchant </strong>
							</h3>
						</div>


					</div>
				</div>
			</div>
		</div>
		<input type="hidden" id="emailChck" value="">
		<form:form method="post"
			action="admin/addMerchant?${_csrf.parameterName}=${_csrf.token}"
			commandName="merchantt" name="form1" id="form1"
			enctype="multipart/form-data">



			<div class="row">
				<div class="col s12">
					<div class="card border-radius">
						<div class="card-content padding-card">
							<div class="d-flex align-items-center">
								<h5>Merchant Details</h5>
							</div>


							<div class="row">

								<div class="input-field col s12 m4 l4">

									<form:select class="form-control" name="salutation"
										path="salutation" id="salutation"
										onchange="changeStyle(this);">
										<form:option selected="true" disabled="disabled" value="">- Select Salutation -</form:option>
										<form:option value="Miss">Miss</form:option>
										<form:option value="Mr">Mr</form:option>
										<form:option value="Mrs">Mrs</form:option>
									</form:select>
									<label for="first_name">Title</label>
								</div>


								<div class="input-field col s12 m4 l4">
									<form:input type="text" id="name" name="name" path="name"
										placeholder="Contact Person Name"
										onchange="changeStyle(this);" />
									<label for="first_name">Contact Person Name</label>
								</div>
								<div class="input-field col s12 m4 l4">
									<form:input type="text" id="contactNo" name="contactNo"
										path="contactNo" placeholder="Contact Number"
										onchange="changeStyle(this);" />
									<label for="name">Contact Number</label>
								</div>

							</div>

							<div class="row">

								<div class="input-field col s12 m4 l4">
									<form:input type="text" id="contactIc" name="contactIc"
										path="contactIc" placeholder="Contact Person IC"
										onchange="changeStyle(this);" />
									<label for="name">Contact Person IC</label>
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>

			<div class="row">
				<div class="col s12">
					<div class="card border-radius">
						<div class="card-content padding-card">
							<div class="d-flex align-items-center">
								<h5>Business Details</h5>
							</div>


							<div class="row">

								<div class="input-field col s12 m4 l4">
									<label for="Trading Name">Trading Name</label>
									<form:input type="text" id="tradingName"
										placeholder="Trading Name" name="tradingName"
										path="tradingName" onchange="changeStyle(this);" />
								</div>
								<div class="input-field col s12 m4 l4">

									<form:input type="text" class="form-control" id="email"
										placeholder="Email" name="email" path="email" />
										
										
									<%-- 	<form:input type="text" class="form-control" id="email"
										placeholder="Email" name="email" path="email"
										onfocusout="myFunction()"/> --%>
										
										
									<label for="Email">Email</label>
								</div>
								
								
								<%-- <c:if test="${responseDataOfficeEmail != null}">
									<H4 style="color: #ff4000;" align="center">${responseDataOfficeEmail}</H4>
									<script>
										Swal.fire({
					  					type: 'error',
					  					title: 'Oops...',
					  					text: ' Email Already Exist..',
					  					//footer: '<a href>Why do I have this issue?</a>'
										}).then((confirm) => {
										if (confirm) {
					    						document.getElementById('email').style.border= "1px solid red";
					     						document.getElementById('email').focus();
					  					} 
										});
					
									</script>
								</c:if>
 --%>
								<div class="input-field col s12 m4 l4">
									<label for="Website">Website</label>
									<form:input type="text" class="form-control" id="website"
										placeholder="WebSite" name="website" path="website"
										onchange="changeStyle(this);" />
								</div>

							</div>

							<div class="row">
								<div class="input-field col s12 m4 l4">
									<form:input type="text" id="businessRegNo" name="businessRegNo"
										path="businessRegNo" placeholder="Business Reg-No"
										onchange="changeStyle(this);" />
									<label for="name">Business Reg-No</label>
								</div>
								<div class="input-field col s12 m4 l4">
									<form:input type="text" id="businessName" name="businessName"
										path="businessName" placeholder="Business Reg-Name"
										onchange="changeStyle(this);" />
									<label for="name">Business Reg-Name</label>
								</div>
								<div class="input-field col s12 m4 l4">
									<form:select id="businessType" name="businessType"
										path="businessType" onchange="changeStyle(this);">
										<form:option value="">-Select Business Type-</form:option>
										<form:option value="Sole Proprietor">Sole Proprietor</form:option>
										<form:option value="Partnership">Partnership</form:option>
										<form:option value="Private Limited">Private Limited</form:option>
										<form:option value="Limited">Limited</form:option>
										<form:option value="Association">Association</form:option>
										<form:option value="LLP">LLP</form:option>
									</form:select>
									<label for="Business">Business Type</label>
								</div>
								<div class="input-field col s12 m4 l4">
									<form:select id="natureOfBusiness" path="natureOfBusiness"
										name="natureOfBusiness" onchange="changeStyle(this);">
										<form:option value="">-Select Nature of Business-</form:option>
										<c:forEach items="${natureOfBusinessList}"
											var="natureOfBusinessList">
											<form:option value="${natureOfBusinessList}">${natureOfBusinessList}</form:option>
										</c:forEach>
									</form:select>
									<label for="Nature">Nature of Business</label>
								</div>
								<div class="input-field col s12 m4 l4">
									<label for="name">Business Address</label>
									<form:textarea rows="3" class="form-control"
										id="businessAddress" name="businessAddress"
										path="businessAddress" onchange="changeStyle(this);"></form:textarea>
								</div>
							</div>

							<div class="row">

								<div class="input-field col s12 m4 l4">
									<label for="first_name">Business City</label>
									<form:input type="text" class="form-control" id="businessCity"
										placeholder="Business City" name="businessCity"
										path="businessCity" onchange="changeStyle(this);" />
								</div>
								<div class="input-field col s12 m4 l4">
							<!-- 		<form:select class="form-control" name="businessState"
										path="businessState" id="businessState"
										onchange="changeStyle(this);">

										<form:option value="">-Select State-</form:option>
										<c:forEach items="${stateList}" var="stateList">
											<form:option value="${stateList}">${stateList}</form:option>
										</c:forEach>
									</form:select>  -->
									
									 <form:input type="text" class="form-control"
										id="businessState" placeholder="Business State"
										name="businessState" path="businessState"
										onchange="changeStyle(this);" />
									
									<label for="name">Business State</label>
								</div>
								<div class="input-field col s12 m4 l4">
									<label for="name">Business PostalCode</label>
									<form:input type="text" class="form-control"
										id="businessPostCode" placeholder="Business PostCode"
										name="businessPostCode" path="businessPostCode"
										onchange="changeStyle(this);" />
								</div>
								<div class="input-field col s12 m4 l4">
									<label for="first_name">Business Country</label>
									<form:input type="text" class="form-control"
										id="businessCountry" placeholder="Business Country"
										name="businessCountry" path="businessCountry"
										onchange="changeStyle(this);" />
								</div>


							</div>


						</div>
					</div>
				</div>
			</div>

			<div class="row">
				<div class="col s12">
					<div class="card border-radius">
						<div class="card-content padding-card">
							<div class="d-flex align-items-center">
								<h5>Owner Details</h5>
							</div>


							<div class="row">


								<div class="input-field col s12 m4 l4">

									<form:select name="ownerSalutation1" path="ownerSalutation1"
										id="ownerSalutation1" onchange="changeStyle(this);">
										<form:option value="">-Select Salutation-</form:option>
										<form:option value="Miss">Miss</form:option>
										<form:option value="Mr">Mr</form:option>
										<form:option value="Mrs">Mrs</form:option>
									</form:select>
									<label>Owner Salutation</label>
								</div>
								<div class="input-field col s12 m4 l4">
									<label for="OwnerName1">Owner/Partner Name</label>
									<form:input type="text" id="ownerName1"
										placeholder="OwnerName" name="ownerName1" path="ownerName1"
										onchange="changeStyle(this);" />
								</div>
								<div class="input-field col s12 m4 l4">
									<label for="ContactNo1">Owner/Partner Contact</label>
									<form:input type="text" id="ownerContactNo1"
										placeholder="OwnerContactNo" name="ownerContactNo1"
										path="ownerContactNo1" onchange="changeStyle(this);" />
								</div>
								<div class="input-field col s12 m4 l4">
									<label for="NRIC1">NRIC/ Passport</label>
									<form:input type="text" id="passportNo1"
										placeholder="PassportNo" name="passportNo1"
										path="passportNo1" onchange="changeStyle(this);" />
								</div>

							</div>


						</div>
					</div>
				</div>
			</div>


			<div class="row">
				<div class="col s12">
					<div class="card border-radius">
						<div class="card-content padding-card">
							<div class="d-flex align-items-center">
								<h5>Account Details</h5>
							</div>


							<div class="row">
								<div class="input-field col s12 m4 l4">
									<!-- <input id="Email" type="text" class="validate"> -->
									<label for="Bank Name">Bank Name</label>
									<form:input type="text" id="bankName" placeholder="Bank Name"
										name="bankName" path="bankName" onchange="changeStyle(this);" />
								</div>

								<div class="input-field col s12 m4 l4">
									<!-- <input id="Account No" type="text" class="validate"> -->
									<label for="Account No">Account No</label>
									<form:input type="text" class="form-control" id="bankAccNo"
										placeholder="Account No" name="bankAccNo" path="bankAccNo"
										onchange="changeStyle(this);" />
								</div>

							</div>

							<div class="row">
								<div class="col s12 m4 l4"></div>
								<div class="col s12 m4 l4">
									<button class="submitBtn" style="margin-top: 15px !important"
										type="submit" onclick="return loadSelectData();">Submit</button>
								</div>
								<div class="col s12 m4 l4"></div>
							</div>


						</div>
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


		</form:form>
	</div>
</body>
</html>