<%@page
	import="com.mobiversa.payment.controller.MerchantWebController"%>

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
<link href="https://fonts.googleapis.com/icon?family=Material+Icons" rel="stylesheet">
    <link href="http://maxcdn.bootstrapcdn.com/bootstrap/3.2.0/css/bootstrap.min.css" rel="stylesheet">
    <script src="http://ajax.googleapis.com/ajax/libs/jquery/1.7.1/jquery.min.js"></script>
<link rel="stylesheet" href="http://cdn.datatables.net/1.10.2/css/jquery.dataTables.min.css"></style>
<script type="text/javascript" src="http://cdn.datatables.net/1.10.2/js/jquery.dataTables.min.js"></script>
<script type="text/javascript" src="http://maxcdn.bootstrapcdn.com/bootstrap/3.2.0/js/bootstrap.min.js"></script> 
    <link href="${pageContext.request.contextPath}/resourcesNew1/assets/libs/chartist/dist/chartist.min.css" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/resourcesNew1/assets/libs/chartist-plugin-tooltips/dist/chartist-plugin-tooltip.css" rel="stylesheet">
     
    <link href="${pageContext.request.contextPath}/resourcesNew1/assets/extra-libs/c3/c3.min.css" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/resourcesNew1/dist/css/style.css" rel="stylesheet">
     
    <link href="${pageContext.request.contextPath}/resourcesNew1/dist/css/pages/dashboard1.css" rel="stylesheet">
	
	 <script type="text/javascript">
	window.history.replaceState({}, document.title, "/MobiversaAdmin/" + "");
	</script>

<script type="text/javascript">
	jQuery(document).ready(function() {

		
		$('#status').select2();
	});
</script>
<script lang="JavaScript">
function loadCancelData() {
	//alert("fcancel data");
	 document.location.href = "${pageContext.request.contextPath}/admmerchant/recurringList";
	form.submit;
}
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

			//var path = '/ezyRecweb/changePwdezyRec';
			//alert('Test : '+path);
			/* document.forms["myform"].submit(); */
			//form.setAttribute("method", "post");
			//form.setAttribute("action", path);
			//alert("test ");
			//document.location.href = '/ezyRecweb/changePwdezyRec';
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
           <h3 class="text-white">  <strong>Merchant Recurring Details </strong></h3>
          </div>
          
          
        </div>
      </div>
    </div>
    </div>
	 
  <div class="row">
  
 
  
  <div class="col s12">

	 <div class="card border-radius">
        <div class="card-content padding-card">
	
	<form method="post" name="form1"
		action="${pageContext.request.contextPath}/<%=MerchantWebController.URL_BASE%>/updateRecurringStatus"
		onsubmit="return checkPassword(this);"
		commandName="ezyRec">
		<input type="hidden" name="${_csrf.parameterName}"
			value="${_csrf.token}" /> <input type="hidden" name="id"
			value="${ezyRec.id}" />



		<!-- <div style="overflow: auto; border: 1px; width: 100%"> -->



			<div class="content-wrapper">


				<div class="row">

					<div class="col-md-6">

						<div class="card" style="width: 50rem;">
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
							<table class="">

								<tbody>
									<tr>

										<td><label>Merchant Name</label></td>
										<%-- <td>${ezyRec.username}</td> 
										<input type="text" class="form-control" id="username"
											name="username" value="${ezyRec.username}"
											readonly="readonly">--%>
										<td><input type="text" id="merchantName" readonly
											name="merchantName" value="${ezyRec.merchantName}"></td>
									</tr>



									<tr>

										<td><label>MID</label></td>
										<%-- <td>${ezyRec.firstName}</td> --%>
										<td><input type="text" 
											id="mid" name="mid" readonly
											value="${ezyRec.mid}"></td>
									</tr>


									<tr>

										<td><label>TID</label></td>
										<%-- <td>${ezyRec.lastName}</td> --%>
										<td><input type="text" id="tid" readonly
											name="tid" value="${ezyRec.tid}"></td>
									</tr>


									<tr>

										<td><label>Customer Name</label></td>
										<%-- <td>${ezyRec.contact}</td> --%>
										<td><input type="text" id="custName" readonly
											name="custName" value="${ezyRec.custName}"></td>
									</tr>
									<tr>

										<td><label>Card No</label></td>
										<%-- <td>${ezyRec.contact}</td> --%>
										<td><input type="text" id="maskedPan" readonly
											name="maskedPan" value="${ezyRec.maskedPan}"></td>
									</tr>
									<tr>

										<td><label>Frequency</label></td>
										<%-- <td>${ezyRec.contact}</td> --%>
										<td><input type="text" id="period" readonly
											name="period" value="${ezyRec.period}"></td>
									</tr>
									<tr>

										<td><label>No Of Payments</label></td>
										<%-- <td>${ezyRec.contact}</td> --%>
										<td><input type="text" id="installmentCount" readonly
											name="installmentCount" value="${ezyRec.installmentCount}"></td>
									</tr>
	
									<tr>

										<td><label>Start Date</label></td>
										
										<td>
										
										<input type="text"  id="startDate" readonly
											name="startDate" value="${ezyRec.startDate}"></td>
									</tr>

									<tr>
										<td><label>End Date</label></td>
										<td><input type="text" readonly
											id="endDate" name="endDate"
											value="${ezyRec.endDate}"></td>

									</tr>
									<tr>
										<td><label>Last Status Updated By</label></td>
										<td><input type="text"readonly
											id="remarks" name="remarks"
											value="${ezyRec.remarks}"></td>

									</tr>


									<tr>

										<td><label>Status</label></td>
										<td>
										
										<select name="status" id="status" onchange="return loadDropD();"
											>
											<option selected value="">Choose</option>
											<option value="ACTIVE">ACTIVE</option>
											<option value="INACTIVE">INACTIVE</option>
										</select></td>

									</tr>

									
								</tbody>
							</table>

						</div>
						<center>
						<input type="hidden" class="form-control"  path="id" id="id" name="id" value="${ezyRec.id}" >
						<button type="submit" class="btn btn-primary" onClick="return loadSelectData()">Submit</button>
						<button type="button" class="btn btn-primary" onClick="loadCancelData()">Cancel</button>
						</center>

					</div>
				</div>
			</div>
		
	</form>
	</div></div></div></div></div>
	

</body>
</html>







