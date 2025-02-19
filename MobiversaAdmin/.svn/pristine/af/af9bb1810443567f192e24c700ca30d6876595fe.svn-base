<%@page
	import="com.mobiversa.payment.controller.MerchantWebMobileController"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
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


function loadData()
{
	
	if(!alphanumeric(document.form1.password,6,15))
	{
	
	return false;
	}
	if(!alphanumeric(document.form1.repassword,6,15))
	{
	
	return false;
	}
	
	}



	function checkPassword() {

		var e = document.getElementById("password").value;
		var e1 = document.getElementById("repassword").value;

		//alert("password :"+e +" : "+e1);
		if (e == null || e1 == null || e == '' || e1 == '') {
			alert("Enter password and confirm password ");
			//form1.submit = false;
			return false;
		} else if (e != e1) {
			alert("Entered password and confirm password is not matching");
			//form1.submit = false;
			return false;
		} else {
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



	<form method="post" name="form1"
		action="${pageContext.request.contextPath}/<%=MerchantWebMobileController.URL_BASE%>/changePwdMobileuser"
		onsubmit="return checkPassword();">
		<input type="hidden" name="${_csrf.parameterName}"
			value="${_csrf.token}" /> <input type="hidden" name="id"
			value="${mobileUser.id}" />



		<c:if test="${responseData  != null}">
			<H4 style="color: #0989af;" align="center">${responseData}</H4>
		</c:if>

  <div style="overflow:auto;border:1px;width:100%">


    
     <div class="content-wrapper">
        
            
        <div class="row">
      
			<div class="col-md-6">
       
				<div class="card" style="width: 50rem;">
              <h3 class="card-title">Merchant Details</h3>
              <table class="table table-striped">

								<tbody>
									<tr>

										<td>Mobile User Name</td>
										<td>${mobileUser.username}</td>
										<td><input type="hidden" class="form-control" id="txtMid"
											name="username" value="${mobileUser.username}"
											readonly="readonly"></td>
									</tr>



									<tr>

										<td>First Name</td>
										<td>${mobileUser.firstName}</td>
										<td><input type="hidden" class="form-control"
											id="firstName" name="firstName"
											value="${mobileUser.firstName}" readonly="readonly"></td>
									</tr>


									<tr>

										<td>Last Name</td>
										<td>${mobileUser.lastName}</td>
										<td><input type="hidden" class="form-control"
											id="lastName" name="lastName" value="${mobileUser.lastName}"
											readonly="readonly"></td>
									</tr>


									<tr>

										<td>Contact No</td>
										<td>${mobileUser.contact}</td>
										<td><input type="hidden" class="form-control"
											id="contact" name="contact" value="${mobileUser.contact}"
											readonly="readonly"></td>
									</tr>


									<tr>

										<td>Email Address</td>
										<td>${mobileUser.email}</td>
										<td><input type="hidden" class="form-control" id="email"
											name="email" value="${mobileUser.email}" readonly="readonly"></td>
									</tr>

									<tr>

										<td>Enter Password</td>
										<td><input type="password" class="form-control"
											id="password" name="password" ></td>

									</tr>


									<tr>

										<td>ReEnter Password</td>
										<td><input type="password" class="form-control"
											id="repassword" name="repassword" ></td>

									</tr>
									</tbody>
							</table>

						</div>
					
				
					
			
				
	
	<button class="submitBtn" type="submit" onclick="return loadData()">Submit</button>
	</div>
	</div>
	</div>
	</div>
	</form>
	
	</body>
</html>







