<%@page import="com.mobiversa.payment.controller.MobileUserController"%>
<%@page import="com.mobiversa.common.bo.Merchant"%>
<%@page import="com.mobiversa.common.bo.MID"%>

 <%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%> 
<html lang="en-US">
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Mobile User Summary</title>    
    
  </head>
<script lang="JavaScript">
	function loadDate(inputtxt, outputtxt) {
		var field = inputtxt.value;
		//var field1 = outputtxt.value;
		// alert(field+" : "+outputtxt.value);
		//document.getElementById("date11").value=field;
		outputtxt.value = field;
		// alert(document.getElementById("date11").value);
	}
</script>

<script lang="JavaScript">

	//hour changes start
	function loadDrophour() {
		var e = document.getElementById("hour1");
		var strUser = e.options[e.selectedIndex].value;
		//alert(strUser);
		document.getElementById("hour").value = strUser;
		//alert("data :" + strUser + " "+ document.getElementById("status1").value);

	}

	//minute changes start
	function loadDropminute() {
		var e = document.getElementById("minute1");
		var strUser = e.options[e.selectedIndex].value;
		document.getElementById("minute").value = strUser;
		//alert("data :" + strUser + " "+ document.getElementById("status1").value);

	}


	function cal() {
		
		alert("returning cal");
		var s1 = document.getElementById("ezywire").checked ? 1 : 0;
		var s2 = document.getElementById("ezyMoto").checked ? 1 : 0;
		var s3 = document.getElementById("ezyRec").checked ? 1 : 0;
		var s4 = document.getElementById("grabPay").checked ? 1 : 0;
		/* var s5 = document.getElementById("mobiPass").checked ? 1 : 0;
		var s6 = document.getElementById("ezyWay").checked ? 1 : 0;
		var s7 = document.getElementById("ezyPOD").checked ? 1 : 0;
		var s8 = document.getElementById("ezyAuth").checked ? 1 : 0;
		var s9 = document.getElementById("ezyPass").checked ? 1 : 0; */

		/* var product = s1 + "," + s2 + "," + s3 + "," + s4 + "," + s5 + "," + s6
				+ "," + s7 + "," + s8+ "," + s9; */
				
				var product = s1 + "," + s2 + "," + s3 + "," + s4;

		document.getElementById('products').value = product;
		alert("returning cal1" +product );
		var title = document.getElementById("title").value;
		alert("returning cal2" +document.getElementById("title").value );
		var date = document.getElementById("datepicker").value;
		
		var hour = document.getElementById("hour").value;
		
		var minute = document.getElementById("minute").value;
		
		var msg = document.getElementById("msg").value;
		
		
		if (title == "") {
			alert("Please enter Title");
			return false;
		} 
		if (date == "") {
			alert("Please select Date");
			return false;
		} 
		if (s1 == "" && s2 == "" && s3 == "" && s4 == "" ) {
			alert("Please select atleast one product");
			return false;
		} 
		/* if (minute == "") {
			alert("Please select minute");
			return false;
		}  */
		
		if (hour == "") {
			document.getElementById("hour").value = "00";
		}
		
		if (minute == "") {
			document.getElementById("minute").value = "00";
		}
		
		if (msg == "") {
			alert("Please enter Message");
			return false;
		} 
		
		alert("returning true");
		return true;
		
	}


	function loadCheck() {
		var e = document.getElementById("all").value;
		if (e == 'unchecked') {
			$('input:checkbox').prop('checked', true);
			document.getElementById('all').value = 'checked';
			var product = 1 + "," + 1 + "," + 1 + "," + 1 + "," + 1 + "," + 1
					+ "," + 1 + "," + 1;

			document.getElementById('products').value = product;

		} else {
			$('input:checkbox').prop('checked', false);
			document.getElementById('all').value = 'unchecked';
			var product = 0 + "," + 0 + "," + 0 + "," + 0 + "," + 0 + "," + 0
					+ "," + 0 + "," + 0;

			document.getElementById('products').value = product;
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
           <h3 class="text-white">  <strong>Notification Details</strong></h3>
          </div>
          
          
        </div>
      </div>
    </div>
    </div>
    
    <form method="post" id="form1" name="form1"
		action="${pageContext.request.contextPath}/device/addNotification?${_csrf.parameterName}=${_csrf.token}">
    <div class="row">
  <div class="col s12">
      <div class="card border-radius">
        <div class="card-content padding-card">
  
				<div class="row">
						<div class="input-field col s12 m3 l3">
								<label for="Business Name">Title<span class="req"> *</span></label> <input
									type="text" placeholder="Title"
									name="title" id="title" value="" maxlength="250">
							
						</div>

						<div class="input-field col s12 m3 l3">
							<label for="date">Date<span class="req"> *</span></label> 
						<input type="hidden" name="date1" id="date1" value="">
						<input type="text" id="from" name="date1"  class="validate datepicker"
						onchange="loadDate(document.getElementById('from'),document.getElementById('date1'))">
						<i class="material-icons prefix">date_range</i>
							
						</div>


						<div class="input-field col s12 m3 l3">

									 <input type="hidden"
										name="hour" id="hour" value=""> <select name="hour1"
										id="hour1" onchange="loadDrophour()">
										<option selected value="00">00</option>
										<option value="01">01</option>
										<option value="02">02</option>
										<option value="03">03</option>
										<option value="04">04</option>
										<option value="05">05</option>
										<option value="06">06</option>
										<option value="07">07</option>
										<option value="08">08</option>
										<option value="09">09</option>
										<option value="10">10</option>
										<option value="11">11</option>
										<option value="12">12</option>
										<option value="13">13</option>
										<option value="14">14</option>
										<option value="15">15</option>
										<option value="16">16</option>
										<option value="17">17</option>
										<option value="18">18</option>
										<option value="19">19</option>
										<option value="20">20</option>
										<option value="21">21</option>
										<option value="22">22</option>
										<option value="23">23</option>
									</select>
									<label >Hour<span class="req"> *</span></label>
								</div>
							<div class="input-field col s12 m3 l3">

									<input
										type="hidden" name="minute" id="minute" value=""> <select
										name="minute1" id="minute1" onchange="loadDropminute()">
										<option selected value="00">00</option>
										<option value="15">15</option>
										<option value="30">30</option>
										<option value="15">45</option>
									</select>
									<label >Minute<span class="req"> *</span></label> 
								
							</div>

						</div>

					</div></div></div>
					<style>
				.export_div .select-wrapper { width:65%;float:left;}
				.datepicker { width:80% !important;}				
				.select-wrapper .caret { fill: #005baa;}		
				 .blue-btn { background-color:#005baa; color:#fff;border-radius:20px;}
				.button-class { float:right;}
				.float-right {float:right; }
				
				
/* The container */
.container {
  display: block;
  position: relative;
  padding-left: 35px;
  margin-bottom: 12px;
  cursor: pointer;
  font-size: 22px;
  -webkit-user-select: none;
  -moz-user-select: none;
  -ms-user-select: none;
  user-select: none;
}

/* Hide the browser's default checkbox */
.container input {
  position: absolute;
  opacity: 0;
  cursor: pointer;
  height: 0;
  width: 0;
}

/* Create a custom checkbox */
.checkmark {
  position: absolute !important;
  top: 0 !important;
  left: 0 !important;
  height: 25px !important;
  width: 25px !important;
  background-color: #eee !important;
  padding-left: 0px !important;
}

/* On mouse-over, add a grey background color */
.container:hover input ~ .checkmark {
  background-color: #ccc !important;
}

/* When the checkbox is checked, add a blue background */
.container input:checked ~ .checkmark {
  background-color: #fff!important;
}

/* Create the checkmark/indicator (hidden when not checked) */
.checkmark:after {
  content: "";
  position: absolute;
  display: none;
}

/* Show the checkmark when checked */
.container input:checked ~ .checkmark:after {
  display: block;
}

/* Style the checkmark/indicator */
.container .checkmark:after {
  left: 9px;
  top: 5px;
  width: 5px;
  height: 10px;
  border: solid white;
  border-width: 0 3px 3px 0;
  -webkit-transform: rotate(45deg);
  -ms-transform: rotate(45deg);
  transform: rotate(45deg);
}
				</style>
				</div>
<div class="row">
  <div class="col s12">
      <div class="card border-radius">
        <div class="card-content padding-card">					
					<div class="row">
						<div class="input-field col s12 m3 l3">

								<label for="Business Address">Notification<span class="req"> *</span></label>
								<textarea rows="15" cols="35" name="msg"
									id="msg" value=""></textarea>


							</div>
						
					</div></div></div></div></div>
						<script>
	$('.pickadate-clear-buttons').pickadate({
    close: 'Close Picker', 
	formatSubmit: 'dd/mm/yyyy',
	
   
});
/* $('.datepicker').pickadate(); */

</script>
<div class="row">
  <div class="col s12">
      <div class="card border-radius">
        <div class="card-content padding-card">						

					<div class="row">
						 <div class="input-field col s3 ">
								<label class="container">All 
									
									<input class="checkboxNotify"
									type="checkbox" placeholder="All Products" name="all" id="all"
									value="unchecked" onchange="loadCheck()">
									 <span class="checkmark"></span>
								</label>
							</div>
						

						 <div class="input-field col s3 ">
								<label class="container">EZYWIRE <input
									type="checkbox" class="checkboxNotify" placeholder="Ezywire" name="ezywire"
									id="ezywire" value="" /> 
									 <span class="checkmark"></span>
								</label>
							</div>
							<input type="hidden" name="products"
									id="products" value="">
						 <div class="input-field col s3 ">
								<label class="container">EZYMOTO <input 
									type="checkbox" class="checkboxNotify" placeholder="EzyMoto" name="ezyMoto"
									id="ezyMoto" value="" />
									 <span class="checkmark"></span></label>
							</div>
						 <div class="input-field col s3 ">
								<label class="container">EZYREC <input 
									type="checkbox" class="checkboxNotify" placeholder="EzyRec" name="ezyRec" id="ezyRec"
									value="" />
									 <span class="checkmark"></span></label>
						
							</div>
							
						 <div class="input-field col s3 ">
								<label class="container">GRABPAY <input
									type="checkbox" class="checkboxNotify" placeholder="GrabPay"
									name="grabPay" id="grabPay" value="" />
									 <span class="checkmark"></span></label>
							</div>
						</div>
						<div class="row">
						<div class="input-field col s12 m3 l3">
					  <div class="button-class"  style="float:left; margin-top:35px;">
						<button class="submitBtn" id="buttonSub" type="submit"
				onclick="return cal();">Submit</button>
				</div>
				</div>
					</div>

				</div>
			</div>

			
		</div>

</div>
	</form>
	</div>



</body> 
</html>