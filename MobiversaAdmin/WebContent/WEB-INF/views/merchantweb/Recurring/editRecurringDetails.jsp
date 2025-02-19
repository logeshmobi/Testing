<%@page
	import="com.mobiversa.payment.controller.MerchantMotoTransactionController"%>

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
	

<script lang="JavaScript">
function loadCancelData() {
	//alert("fcancel data");
	 document.location.href = "${pageContext.request.contextPath}/transactionMoto/recurringList";
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
		action="${pageContext.request.contextPath}/<%=MerchantMotoTransactionController.URL_BASE%>/updateRecurringStatus"
		onsubmit="return checkPassword(this);"
		commandName="ezyRec">
		<input type="hidden" name="${_csrf.parameterName}"
			value="${_csrf.token}" /> <input type="hidden" name="id"
			value="${ezyRec.id}" />


								<c:if test="${responseData  != null}">
									<H4 style="color: #0989af;" align="center">${responseData}</H4>
								</c:if>
								<c:if test="${responseData1  != null}">
									<H4 style="color: #0989af;" align="center">${responseData1}</H4>
								</c:if>
								
								<c:if test="${responseData2  != null}">
									<H4 style="color: #0989af;" align="center">${responseData2}</H4>
								</c:if>
			<div class="row">
							<div class="input-field col s12 m6 l6 ">					
							

								<label>Merchant Name</label>

										<input type="text" id="merchantName" readonly
											name="merchantName" value="${ezyRec.merchantName}">
									</div>

								<div class="input-field col s12 m6 l6 ">			
										<label>MID</label>
										<input type="text" 
											id="mid" name="mid" readonly
											value="${ezyRec.mid}"></div>
									<div class="input-field col s12 m6 l6 ">			
										<label>TID</label>
										<input type="text"  id="tid" readonly
											name="tid" value="${ezyRec.tid}"></div>
									<div class="input-field col s12 m6 l6 ">			
										<label>Customer Name</label>
										<input type="text" id="custName" readonly
											name="custName" value="${ezyRec.custName}"></div>
									<div class="input-field col s12 m6 l6 ">			
									<label>Card No</label>
										<input type="text"  id="maskedPan" readonly
											name="maskedPan" value="${ezyRec.maskedPan}"></div>
									<div class="input-field col s12 m6 l6 ">			

										<label>Frequency</label>
										<input type="text"  id="period" readonly
											name="period" value="${ezyRec.period}"></div>
									<div class="input-field col s12 m6 l6 ">			

									<label>No Of Payments</label>
										<%-- <td>${ezyRec.contact}</td> --%>
										<input type="text" id="installmentCount" readonly
											name="installmentCount" value="${ezyRec.installmentCount}">
											</div>
									<div class="input-field col s12 m6 l6 ">			

										<label>Start Date</label>
										
										<input type="text" id="startDate" readonly
											name="startDate" value="${ezyRec.startDate}">
											</div>
									<div class="input-field col s12 m6 l6 ">			
										<label>End Date</label>
										<input type="text" readonly
											id="endDate" name="endDate"
											value="${ezyRec.endDate}">
										</div>
									<div class="input-field col s12 m6 l6 ">			
										<label>Last Status Updated By</label>
										<input type="text" readonly
											id="remarks" name="remarks"
											value="${ezyRec.remarks}">
									</div>
									<div class="input-field col s12 m6 l6 ">			

										
										<input type="hidden"  name="status" id="status" value="${ezyRec.status}"/>
										<select name="status1" id="status1" onchange="document.getElementById('status').value=document.getElementById('status1').value;"
											>
											<option selected value="">Choose</option>
											<option value="ACTIVE">ACTIVE</option>
											<option value="INACTIVE">INACTIVE</option>
										</select>
										<label>Status</label>

									</div></div>
						<center>
						<input type="hidden" class="form-control"  path="id" id="id" name="id" value="${ezyRec.id}" >
						<button type="submit" class="btn btn-primary blue-btn" onClick="return loadSelectData()">Submit</button>
						<button type="button" class="btn btn-primary blue-btn" onClick="loadCancelData()">Cancel</button>
						</center>
<style>
							
				.select-wrapper .caret { fill: #005baa;}
							
				 .blue-btn { background-color:#005baa; color:#fff;border-radius:20px;}
				.button-class { float:right;}
				.float-right {float:right; }
				</style>
					
				
			
			
			</form>
		</div>
	
</div></div></div></div>
</body>
</html>







