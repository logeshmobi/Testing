<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>

<html lang="en-US">
<head>
<meta charset="UTF-8">
<meta content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no" name="viewport">

<script type="text/javascript">
	history.pushState(null, null, "");
	window.addEventListener('popstate', function() {
		history.pushState(null, null, "");

	});

	jQuery(document).ready(function() {
		$('#mid1').select2();
	});
	
</script>


<script lang="JavaScript">
	function loadContinue() {
		//alert("test"+document.getElementById("txnType").value);
		var e = document.getElementById("from").value;
		
		var fromDate = new Date(e);//.toDateString("yyyy-MM-dd");
		var fromday = fromDate.getDate();
		var frommon = fromDate.getMonth() + 1;
		var fromyear = fromDate.getFullYear();


		var fromdateString = (fromday <= 9 ? '0' + fromday : fromday) + '/' + (frommon <= 9 ? '0' + frommon : frommon) + '/' + fromyear;
		
		if (e == '' ) {
			alert("Please Select Date to continue");
			//form.submit == false;
		} else {
			/* document.getElementById("dateval1").value = e;
			document.getElementById("dateval2").value = e1;
			document.getElementById("txnType").value = e2; */
			document.location.href = '${pageContext.request.contextPath}/MDR/regenerateForm?date='+ fromdateString ;
		    form.submit;
			//document.getElementById("dateval1").value = e;
			//document.getElementById("dateval2").value = e1;

		} 
	} 
	
	function loadSelectData() {
		
		var mid1 = document.getElementById("mid1").value;
		
		var settleType1  = mid1.split(/[~]/);
		var settleType = settleType1[1];
		var mid = settleType1[0];
		
		var date = document.getElementById("date1").value;
		var mailTo = document.getElementById("mailTo1").value;
		var mailCC = document.getElementById("mailCC1").value;
		var merchantFile = "";
		var mdrFile = "";
		var deductionFile = "";
		var csvFile = "";
		
		
		
		if (document.getElementById('merchantFile1').checked) {
			//alert("checked");
			merchantFile = document.getElementById('merchantFile1').value;
		}else{
			//alert("unchecked");
			merchantFile = "No";
		}
		
		if (document.getElementById('mdrFile1').checked) {
			mdrFile = document.getElementById('mdrFile1').value;
		}else {
			mdrFile = "No";
		}
		
		
		if (document.getElementById('deductionFile1').checked) {
			deductionFile = document.getElementById('deductionFile1').value;
		}else{
			deductionFile = "No";
		}
		
		
		if (document.getElementById('csvFile1').checked) {
			csvFile = document.getElementById('csvFile1').value;
		}else{
			csvFile = "No";
		}
		
		
		/* alert("mid:::::::::"+mid);
		alert("settlePeriod"+settlePeriod);
		alert("date::::::::"+date);
		alert("mailTo::::::"+mailTo);
		alert("mailCC::::::"+mailCC);
		alert("merchantFile"+merchantFile);
		alert("mdrFile:::::"+mdrFile);
		alert("deductionFile"+deductionFile);
		alert("csvFile::::::"+csvFile); */
		
		//alert("settleType"+settleType);
		document.getElementById('mid').value = mid;
		document.getElementById('date').value = date;
		document.getElementById('mailCC').value = mailCC;
		document.getElementById('mailTo').value = mailTo;
		document.getElementById('merchantFile').value = merchantFile;
		document.getElementById('mdrFile').value = mdrFile;
		document.getElementById('deductionFile').value = deductionFile;
		document.getElementById('csvFile').value = csvFile;
		if (typeof settleType !== "undefined") {
			if (settleType == '' ) {
				//alert("settleType Empty");
				document.getElementById("settleType").value = 'Normal';
			}else{
				//alert("settleType yes");
				document.getElementById("settleType").value = settleType;
			}
			
		}else if (typeof settleType === "undefined") {
			//alert("settleType NO");
			document.getElementById("settleType").value = 'Normal';
			
		}
		
		
		//alert("mid:"+mid);
		/* if (mid == '' || mid == null) {
			alert("Please Select Merchant");
			document.getElementById("mid1").focus();
			return false;
		} */
		
		$("#myform").submit();
		//return true;
	}

	function loadDate(inputtxt, outputtxt) {

		// alert("test data123");
		var field = inputtxt.value;
		//var field1 = outputtxt.value;
		//alert(field+" : "+outputtxt.value);
		//document.getElementById("date11").value=field;
		outputtxt.value = field;
		//alert(outputtxt.value);
		// alert(document.getElementById("date11").value);
	}
	
	/* function checkDate(){
		
		var today = new Date();
		//alert("today ::::::::"+today);
		    month = '' + (today.getMonth() + 1);
	        day = '' + today.getDate();
	        year = today.getFullYear();
	       // alert("month ::::::::"+month);
	       // alert("day ::::::::"+day);
	      //  alert("year ::::::::"+year);
	        if (month.length < 2) 
	            month = '0' + month;
	        if (day.length < 2) 
	            day = '0' + day;
	        today= [day, month, year].join('/');
	       // alert("pharsed today ::::::::"+today);
		document.getElementById("datepicker").value=today;
		document.getElementById("date11").value=today;
		
	} */
	

</script>
</head>
<!-- <body onload="checkDate();"> -->
<body>
<div class="container-fluid">  
 <div class="row">
  <div class="col s12">
      <div class="card blue-bg text-white">
        <div class="card-content">
          <div class="d-flex align-items-center">
          <c:choose>
			<c:when test="${responseData != null }">
				
				<h3 class="text-white">  <strong>${responseData}</strong></h3>
				
			</c:when>
			<c:otherwise>
				
				<h3 class="text-white"><strong>File Regeneration</strong></h3>
				
			</c:otherwise>
		</c:choose>
          </div>
          
          
        </div>
      </div>
    </div>
    </div>
<div class="row">
  <div class="col s12">
      <div class="card border-radius">
        <div class="card-content padding-card">

				<div class="row">
					<div class="input-field col s12 m3 l3">
						<label for="from" style="margin:0px;"> Date </label>
						<input type="hidden"
									name="date11" id="date11" <c:out value="${fromDate}"/>> 
						<input type="text" id="from" name="date1"  class="validate datepicker"
						onchange="loadDate(document.getElementById('from'))">
						<i class="material-icons prefix">date_range</i>
					</div>

					<div class="input-field col s12 m3 l3">
						<input type="hidden" name="date1" id="dateval1"> <input
							type="hidden" name="date2" id="dateval2">
						<button class="btn btn-primary blue-btn" type="button"
							onclick="return loadContinue();">Continue</button>
					</div>
				</div>
						</div>
					</div>
				</div>
				<style>
				.export_div .select-wrapper { width:65%;float:left;}
				
				.datepicker { width:80% !important;}				
				.select-wrapper .caret { fill: #005baa;}
				
				.addUserBtn,.addUserBtn:hover {
				background-color: #fff;border: 1px solid #005baa;border-radius: 20px;color: #005baa; font-weight:600;}
				 .blue-btn { background-color:#005baa; color:#fff;border-radius:20px;}
				.button-class { float:right;}
				.float-right {float:right; }
				</style>
			</div>
	    <script>
	$('.pickadate-clear-buttons').pickadate({
    close: 'Close Picker', 
	formatSubmit: 'MM/dd/yyyy',
   
});
$('.datepicker').pickadate();

</script>	
		
	
<form method="post" id="myform" name="myform" action="${pageContext.request.contextPath}/MDR/generateFile?${_csrf.parameterName}=${_csrf.token}">


<input type="hidden" id="mid" name="mid" value="123456789">
<input type="hidden" id="date" name="date" value="16/04/2020">
<input type="hidden" id="merchantFile" name="merchantFile" value="Yes">
<input type="hidden" id="mdrFile" name="mdrFile" value="Yes">
<input type="hidden" id="deductionFile" name="deductionFile" value="No">
<input type="hidden" id="csvFile" name="csvFile" value="No">
<input type="hidden" id="settleType" name="settleType" value="">
<input type="hidden" id="mailTo" name="mailTo" value="John@qmail.com">
<input type="hidden" id="mailCC" name="mailCC" value="Johnyy@qmail.com">

</form>
		
		

		<c:if test="${not empty settleDate}">
		<div class="row">
  <div class="col s12">
      <div class="card border-radius">
        <div class="card-content padding-card">	
			<div class="d-flex align-items-center">

			<h2 class="card-title" style="color: blue;">File
				Regeneration for the Date : <c:out value="${settleDate}"/></h2>
				</div>
				<div class="d-flex align-items-center">
			<h2 class="card-title" ><span style="font-weight: bold;"> Mail To :</span> <span> finance@mobiversa.com
			</div>
			<div class="d-flex align-items-center">	
			<h2 class="card-title" ><span style="font-weight: bold;"> Mail CC :</span> <span> prem@mobiversa.com,sangeetha@mobiversa.com,nandha@mobiversa.com </span></h2>	
				</div>				
						
									<div class="row">
									<input type="hidden" name="date1"  path="date1" id="date1"  <c:out value="${settleDate}"/>  />
								<div class="input-field col s12 m3 l3">
									Merchant Details
								</div>
								<div class="input-field col s12 m5 l5">
										<select
											name="mid1" id="mid1" class="browser-default select-filter">
											<option selected value=""><c:out value="Choose" /></option>
											<c:forEach items="${merchant1}" var="merchant1">
												<option value="${merchant1.mid}~${merchant1.settleType}">${merchant1.merchantName}~${merchant1.merchantEmail}~${merchant1.settleType}</option>
											</c:forEach>
										</select>
									</div>	
							
							</div>
					
					<div class="row">
					 <div class="input-field col s12 m3 l3"></div>

        <!-- Script -->
        <script>
        $(document).ready(function(){
            
            // Initialize select2
            $("#selUser").select2();
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
 .select-search .select-wrapper input {
  display:none !important;
  }
.select2-results__options li{
	list-style-type: none;												
	padding: 4px 0px 4px 4px !important;
	font-size: 18px !important;
}
.select2-results ul{
	max-height:250px;
	
	curser:pointer;
}
.select2-results ul li:hover{
	background-color: #005baa !important;
	
	color:#fff !important; 
}
.select-search-hidden .select2-container{
	display:none !important;
}

</style>			
					</div></div>
		 
        				<div class="row">	
									<div class="input-field col s12 m6 l6 "> Merchant File :
										<p><label><input name="merchantFile1" path="merchantFile1"
										value="Yes" id="merchantFile1"   type="radio" checked /><span>Yes</span></label>   
										<label><input name="merchantFile1" value="No"  
										id="merchantFile1"  path="merchantFile1" type="radio" /><span>No</span></label></p>
									</div>
									<div class="input-field col s12 m6 l6 "> MDR File :
										<p><label><input name="mdrFile1" path="mdrFile1"
										value="Yes" id="mdrFile1"   type="radio" checked /><span>Yes</span></label>   
										<label><input name="mdrFile1" value="No"  
										id="mdrFile1"  path="mdrFile1" type="radio" /><span>No</span></label></p>
									</div>
									<div class="input-field col s12 m6 l6 "> Deduction File :
										<p><label><input name="deductionFile1" path="deductionFile1"
										value="Yes" id="deductionFile1"   type="radio" checked /><span>Yes</span></label>   
										<label><input name="deductionFile1" value="No"  
										id="deductionFile1"  path="deductionFile1" type="radio" /><span>No</span></label></p>
									</div>
									
									<div class="input-field col s12 m6 l6 "> CSV File :
										<p><label><input name="csvFile1" path="csvFile1"
										value="Yes" id="csvFile1"   type="radio" checked /><span>Yes</span></label>   
										<label><input name="csvFile1" value="No"  
										id="csvFile1"  path="csvFile1" type="radio" /><span>No</span></label></p>
									</div>

								</div>
							

								<div class="row">
									<div class="input-field col s12 m6 l6 ">

											<button class="btn btn-primary blue-btn" type="submit" value="Submit"
												onclick="return loadSelectData();">Submit</button>


										</div>
									</div>
</div></div></div>
	</div>
		</c:if>
</div>
</body>