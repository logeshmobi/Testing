<%@page
	import="com.mobiversa.payment.controller.MerchantWebReaderController"%>

<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>
<html lang="en-US">
<head>
<meta charset="UTF-8">
<meta
	content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no"
	name="viewport">

	 <script type="text/javascript">
	window.history.replaceState({}, document.title, "/MobiversaAdmin/" + "");
	</script>
	
	
<!-- <script type="text/javascript">
	 function validate()
	 {
	// alert("validating ..."+document.getElementById("datepicker").value);
	 document.getElementById("error1").style.display= 'none';
	 document.getElementById("error2").style.display= 'none';
	
	 
	 }
	function validateEmpty()
	{
	 var datepicker=document.getElementById("datepicker").value;
	 alert("validate.."+datepicker);
	 if(datepicker==null)
		{
		datepicker.innerHtml="Empty fields should not be Allowed";
		return false;
	 }
	
	}
	 </script>
	  -->

<!--  <script type="text/javascript">
	 
	 $('#datepicker').on('change invalid', function() {
    var textfield = $(this).get(0);
    
    // 'setCustomValidity not only sets the message, but also marks
    // the field as invalid. In order to see whether the field really is
    // invalid, we have to remove the message first
    textfield.setCustomValidity('');
    
    if (!textfield.validity.valid) {
      textfield.setCustomValidity('Empty Fields not Allowed..!');  
    }
});
	 
	 
	 
	 
	</script> -->

<!-- <script type="text/javascript">
	function checkDOB() {

		var myDate = document.getElementById("datepicker").value;

		//alert("myDate Date: " + myDate);

		//alert(myDate);
		var currentDate = new Date();
		var date = currentDate.getDate();
		var month = currentDate.getMonth(); //Be careful! January is 0 not 1
		var year = currentDate.getFullYear();
		
		function pad(n) {
			return n < 10 ? '0' + n : n;
		}
		
		var today = pad(month + 1) + "/" + pad(date) + "/" + year;
		//alert("today date "+today);
		//alert("my date :" + myDate + " current date: " + today);

		if ((myDate != null || myDate != ' ')
				&& (today != null || today != ' ')) 
				{

			//alert("my date :" + myDate + " current date: " + today);
			
			
			if (today < myDate) 
			{
				//alert("my date :" + myDate + " current date: " + today);
				alert("you cannot enter the date in future...!");

				//document.getElementById("error2").style.display= 'block';

				return false;
			} else {
				//alert("true date");
				//alert("cehck else"+myDate > today1);
				//document.getElementById("error2").style.display= 'none';
				return true;

			}

		}
	}
</script> -->

<script lang="JavaScript">
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
			alert(inputtxt.name + ' must have characters and numbers only');
			// uadd.focus();  
			return false;
		}
	}

	function loadData() {

		var e = document.getElementById("password").value;
		var e1 = document.getElementById("repassword").value;
		if (e != null && e != '') {
			if (!alphanumeric(document.form1.password, 6, 15)) {

				return false;
			}
		}
		if (e1 != null && e1 != '') {
			if (!alphanumeric(document.form1.repassword, 6, 15)) {

				return false;
			}
		}

	}
	
	function isFutureDate(idate){
		//alert(idate);
	    var today = new Date().getTime(),
	        idate = idate.split("/");
	
	    idate = new Date(idate[2], idate[1] - 1, idate[0]).getTime();
	    //alert("mydate : "+idate+"   : sysdate "+today);
	    return (today - idate) < 0 ? true : false;
	}

	function checkPassword(form1) {

		//var date=document.getElementById("datepicker").value;
		//alert("datepicker: "+date);
		var e = document.getElementById("password").value;
		var e1 = document.getElementById("repassword").value;

		/* if(date==null || date==' ')
		{
		//alert("Empty fields not Allowed..!");
		$('#datepicker').after(
		
					'<p><font color="red">Empty fields not Allowed..!</font></p>');
		return false;
		} */
		var datepicker = document.getElementById("datepicker").value;
		//alert("date :"+datepicker);
		if((isFutureDate(datepicker)))
		{
			//alert("you cannot enter the date in future...!");
			document.getElementById("error2").style.display= 'block';
			return false;
		}
		/* else
		{
		document.getElementById("error2").style.display= 'none';
		return true;
		} */
		//alert("datepicker validateDateOfBirth"+datepicker);

		/* if(datepicker==null || datepicker==' ')
		{
		//alert("datepicker validateDateOfBirth null"+datepicker);
		document.getElementById("error1").style.display= 'block';
		return false;
		}
		else
		{
		//alert("datepicker validateDateOfBirth not null"+datepicker);
		document.getElementById("error1").style.display= 'none';
		return true;
		
		} */

		//var myDate = document.getElementById("datepicker").value;

		//alert("myDate Date: " + myDate);

		
		//alert(myDate);
	/* 	var currentDate = new Date();
		var date = currentDate.getDate();
		var month = currentDate.getMonth(); //Be careful! January is 0 not 1
		var year = currentDate.getFullYear();
		
		function pad(n) {
			return n < 10 ? '0' + n : n;
		}
		
		var today = pad(month + 1) + "/" + pad(date) + "/" + year;
		//alert("today date "+today);
		alert("my date :" + myDate + " current date: " + today);

		if ((myDate != null || myDate != ' ')
				&& (today != null || today != ' ') && (today < myDate) )
				{

			//alert("my date :" + myDate + " current date: " + today+" "+today < myDate);
			//alert("my date :" + myDate + " current date: " + today);
			alert("you cannot enter the date in future...!");

			//document.getElementById("error2").style.display= 'block';
					
				return false;
			} 
			else 
			{
				//alert("true date");
				//alert("cehck else"+myDate > today1);
				//document.getElementById("error2").style.display= 'none';
				return true;

			} */

		

		//alert("password :"+e +" : "+e1);
		if ((e != null || e != '') && (e1 == null && e1 == '')) {
			alert("Enter confirm password ");
			//form1.submit = false;
			return false;
		} else if (e != e1) {
			alert("Entered password and confirm password is not matching");
			//form1.submit = false;
			return false;
		}

		else {
			//alert("else part");

			//var path = '/mobileUserweb/changePwdMobileuser';
			//alert('Test : '+path);
			/* document.forms["myform"].submit(); */
			//form.setAttribute("method", "post");
			//form.setAttribute("action", path);
			//alert("test ");
			//document.location.href = '/mobileUserweb/changePwdMobileuser';
			//form.submit();// = true; 
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
           <h3 class="text-white">  <strong>Mobile User Details</strong></h3>
          </div>
          
          
        </div>
      </div>
    </div>
    </div>

	<form method="post" name="form1"
		action="${pageContext.request.contextPath}/<%=MerchantWebReaderController.URL_BASE%>/editMobileUserLogin"
		onsubmit="return checkPassword(this);"
		commandName="mobileUser">
		<input type="hidden" name="${_csrf.parameterName}"
			value="${_csrf.token}" /> <input type="hidden" name="id"
			value="${mobileUser.id}" />

<div class="row">
 <div class="col s12">
      <div class="d-flex align-items-center">
        	
							<div>
								<c:if test="${responseData  != null}">
									<H4 style="color: #0989af;" align="center">${responseData}</H4>
								</c:if>
								<c:if test="${responseData1  != null}">
									<H4 style="color: #0989af;" align="center">${responseData1}</H4>
								</c:if>
								
								<c:if test="${responseData2  != null}">
									<H4 style="color: #0989af;" align="center">${responseData2}</H4>
								</c:if>
							</div>
							</div></div></div>
							
<div class="row">
	  <div class="col s12">
	      <div class="card border-radius">
	        <div class="card-content padding-card">
	        
	        <div class="row">
	         <div class="input-field col s12 m6 l6 ">
	         <label>Mobile User Name</label>
	         <input type="text" id="username"
				name="username" value="${mobileUser.username}">
				</div>
				
				 <div class="input-field col s12 m6 l6 ">
	         <label>First Name</label>
	        <input type="text"
			id="firstName" name="firstName"
			value="${mobileUser.firstName}">
				</div>
				
						
				 <div class="input-field col s12 m6 l6 ">
	         <label>Last Name</label>
	        <input type="text" id="lastName"
				name="lastName" value="${mobileUser.lastName}">
				</div>
				
						
				 <div class="input-field col s12 m6 l6 ">
	         <label>Contact No</label>
	        <input type="text"  id="contact"
			name="contact" value="${mobileUser.contact}">
				</div>
							
				<div class="input-field col s12 m6 l6 ">
				<label>Date of Birth</label>
				<input type="hidden" name="date11" id="date11"
											value="${mobileUser.dateOfBirth}"> <input type="text"
											 id="datepicker" name="dateOfBirth"
											value="${mobileUser.dateOfBirth}" placeholder="dd/mm/yyyy"
											data-toggle="tooltip" data-placement="right"
											title="Enter date properly, Empty fields should not allowed..!">
											<!--  --> <!-- onsubmit="return validateDateOfBirth(document.getElementById('datepicker'));" >-->
											<span class="error1" id="error1" style="color:blue;font-weight:bold;display:none;" >
										<font color="red">*</font>Empty Fields not Allowed..!</span>
										<span class="error2" id="error2" style="color:blue;font-weight:bold;display:none;" >
										<font color="red">*</font>You cannot enter a date in the future!.</span>
				
				</div>			
						
				<div class="input-field col s12 m6 l6 ">
				<label>Email Address</label>
				<input type="text" id="email"
				name="email" value="${mobileUser.email}">
				</div>
				
				<div class="input-field col s12 m6 l6 ">
				<label>Facebook Id</label>
				<input type="text" 
				id="facebookId" name="facebookId"
				value="${mobileUser.facebookId}">
				</div>	
				<div class="input-field col s12 m6 l6 ">
				<label>Google Id</label>
				<input type="text" id="googleId"
				name="googleId" value="${mobileUser.googleId}">
				</div>	
				
				<div class="input-field col s12 m6 l6 ">

					<label>Enter New App Password</label>
					<input type="password" 
						id="password" name="password">
				</div>	
				<div class="input-field col s12 m6 l6 ">

					<label>ReEnter New App Password</label>
				<input type="password"
						id="repassword" name="repassword">
				</div>			

				</div>						

										

							<button class="btn btn-primary blue-btn" type="submit" value="Submit"
								onsubmit="return loadData();">Submit</button>
								
								<button class="btn btn-primary blue-btn" type="submit" value="Submit">
								<a href="${pageContext.request.contextPath}/readerweb/list/" style="color:white;">Cancel</a></button>
						

<style>						
				.select-wrapper .caret { fill: #005baa;}
							
				 .blue-btn { background-color:#005baa; color:#fff;border-radius:20px;}
				.button-class { float:right;}
				.float-right {float:right; }
				</style>
					</div>
					
				</div>
				
			</div>
			
		</div>
	</form>
</div>
</body>
</html>







