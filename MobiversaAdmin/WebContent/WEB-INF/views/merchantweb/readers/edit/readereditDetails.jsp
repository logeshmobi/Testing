<%@page import="com.mobiversa.payment.controller.MerchantWebReaderController"%>
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

</head>

	
<script lang="JavaScript">

function loadCancelData() {
	//alert("fcancel data");
	 document.location.href = "${pageContext.request.contextPath}/readerweb/list/1";
	form.submit;
}

function loadSelectData()
{


//alert("test data");


//var a = document.getElementById("deviceId").value;
//alert("check deviceId:" + a);
	if(!allLetterSpaceSpecialCharacter(document.form1.contactName, 3, 50))
	{
	
		
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
		alert(inputtxt.name + ' must have alphanumeric and space and special characters with -,&,/,() only');
		//uname.focus();  
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
		alert("Please input the Contact Name  between " + mnlen
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

</script>

<body>
<div class="container-fluid">    
  <div class="row">
  
 
  
  <div class="col s12">
      <div class="card blue-bg text-white">
        <div class="card-content">
          <div class="d-flex align-items-center">
           <h3 class="text-white">  <strong>Edit Reader Details</strong></h3>
          </div>
          
          
        </div>
      </div>
    </div>
    </div>


	<form action="${pageContext.request.contextPath}/<%=MerchantWebReaderController.URL_BASE%>/editReader"
		method="post" id="form1" name="form1">

	<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
 <%-- <input type="hidden" name="deviceId" value="${reader.deviceId}" />   --%>
<div class="row">
  <div class="col s12">
      <div class="card border-radius">
        <div class="card-content padding-card">
            <div class="d-flex align-items-center">
				         
				          </div>
            	
              
               <div class="row">
							<div class="input-field col s12 m6 l6  ">
									<label  for="fName">Device Id</label>
								<input type="text" id="deviceId" name="deviceId" value="${reader.deviceId}">
								</div>
								
								
								
									
								<input type="hidden" id="olddeviceId" name="olddeviceId" value="${reader.deviceId}">
								
								
								
             <div class="input-field col s12 m6 l6  ">
									<label  for="lName">TID</label>
								<input type="text"  id="tid" name="tid" value="${reader.tid}" readonly="readonly">
								</div>
							
							 
							<div class="input-field col s12 m6 l6  ">
									<label  for="fName">Mid</label>
								<input type="text" id="merchantId" name="merchantId" value="${reader.merchantId}" readonly="readonly">
								</div>
							
							
						
							<div class="input-field col s12 m6 l6  ">
									<label  for="contactName">DeviceHolder Name</label>
								<input type="text" class="form-control"  path="contactName" id="contactName" name="contactName" value="${reader.contactName}" >
								</div>
								
							<div class="input-field col s12 m6 l6  ">
							<select name="activeStatus">
										<option value="ACTIVE"
											${reader.activeStatus == "ACTIVE" ? 'selected="selected"' : ''}>ACTIVE</option>
										<option value="SUSPENDED"
											${reader.activeStatus == "SUSPENDED" ? 'selected="selected"' : ''}>SUSPENDED</option>
									</select> <label for="ACTIVE STATUS">ACTIVE STATUS</label>
							
						   </div>	
							</div>	
						
							
			
	<div class="row">
	<div class="input-field col s12 m6 l6  ">
			<button type="submit" class="btn btn-primary blue-btn"
				onClick="return loadSelectData()">Submit</button>
			<button type="button" class="btn btn-primary blue-btn"
				onClick="loadCancelData()">Cancel</button>
				</div>
							</div>
		</div>
		<style>
							
				.select-wrapper .caret { fill: #005baa;}
							
				 .blue-btn { background-color:#005baa; color:#fff;border-radius:20px;}
				.button-class { float:right;}
				.float-right {float:right; }
				</style>
      
</div>
</div>
</div>



	</form>
	</div>
</body>
</html>


