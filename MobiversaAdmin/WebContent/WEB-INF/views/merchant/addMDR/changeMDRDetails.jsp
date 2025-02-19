<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@page import="com.mobiversa.payment.controller.MerchantWebController"%>
<%@page import="com.mobiversa.payment.controller.MDRDetailsController"%>
<%@page import="com.mobiversa.common.bo.Merchant"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<html lang="en-US">
<head>
<meta content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no" name="viewport">
<style>
  
  input:focus { 
    outline: none !important;
    border:1px solid red;
    box-shadow: 0 0 10px #719ECE;
}
</style>
<style>
.error {
	color: red;
	font-weight: bold;
}
</style>

<!-- <script type="text/javascript">
	jQuery(document).ready(function() {
		$('#brand').select2();
		$('#merchantName').select2();
</script> -->

<script lang="JavaScript">

function loadDropDate22() {
	//alert("strUser.value");
	var e = document.getElementById("settlePeriod1");
	var strUser = e.options[e.selectedIndex].value;
	document.getElementById("settlePeriod").value = strUser;
	//alert("data :" + strUser + " "+ document.getElementById("status1").value);

}

/* function loadDropDate11() {
	//alert("strUser.value");
	var e = document.getElementById("brand");

	var strUser = e.options[e.selectedIndex].value;
	document.getElementById("brand1").value = strUser;
	//alert("data :" + strUser + " "+ document.getElementById("status1").value);

}

function loadDropDate() {
	// alert("strUser.value"); 
	var e = document.getElementById("mid");

	var strUser = e.options[e.selectedIndex].value;
	document.getElementById("mid1").value = strUser;
	//alert("data :" + strUser + " "+ document.getElementById("tid1").value);
 
}

function loadDropDate12() {
	//alert("strUser.value");
	var e = document.getElementById("cardtype");

	var strUser = e.options[e.selectedIndex].value;
	document.getElementById("cardtype1").value = strUser;
	//alert("data :" + strUser + " "+ document.getElementById("status1").value);

} */

function load1(){
	var url = "${pageContext.request.contextPath}/MDR/updateMDRDetails";
	$(location).attr('href',url);
}


function checkPay(){
	var payLater = "";
	
	if (document.getElementById('payLater').checked) {
		payLater = document.getElementById('payLater').value;
	}
	if (document.getElementById('payLater1').checked) {
		payLater = document.getElementById('payLater1').value;
	}
	
	if( payLater == 'No') {
		//alert("payLater:::::"+payLater);
        document.getElementById('toPay').style.display='none';
  	}else if ( payLater == 'Yes'){
  		//alert("payLater:::::"+payLater);
  		document.getElementById('toPay').style.display='block'
  	}else{
  		 document.getElementById('toPay').style.display='none';
  	}

}

function changeStyle(id){
//alert("changeing --"+id.value+"--");
var field=id.value;
//alert(" changeing --"+field.length+"--");
//document.getElementById("mid").focus();
if(field.length!=0){
//alert(" changeing --"+field.length+"--");
id.style.border = "1px solid #3FCADB";
}else{
id.style.border = "1px solid #B5B9B9";
}
} 

function loadSelectData(){ 
	
	var mid = document.getElementById("mid").value;
	var brand = document.getElementById("brand").value;
	var settlePeriod = document.getElementById("settlePeriod").value;
	var payLater = "";
	var amount = document.getElementById("amount").value;
	var installment = document.getElementById("installment").value;
	
	if (document.getElementById('payLater').checked) {
		payLater = document.getElementById('payLater').value;
	}
	if (document.getElementById('payLater1').checked) {
		payLater = document.getElementById('payLater1').value;
	}
	
	//alert("payLater:::::"+payLater);
	if( payLater == 'No') {
		
		//alert("mid:::::"+mid);
		if (mid == '') {
			alert("please select MID");
			document.getElementById("mid").focus();
			return false;

		}
		
		//alert("brand::"+brand);
		if (brand == '') {
			alert("please select brand");
			document.getElementById("brand").focus();
			return false;

		}
		
		
		alert("settlePeriod:::"+settlePeriod);
		if (settlePeriod == '') {
			alert("please select Settle Period");
			document.getElementById("settlePeriod1").focus();
			return false;

		}
		
		document.getElementById("amount").value=0;
		document.getElementById("installment").value=0;
		
		
  	}else if ( payLater == 'Yes'){
  		
  		//alert("mid:::::"+mid);
		if (mid == '') {
			alert("please select MID");
			document.getElementById("mid").focus();
			return false;

		}
		
		//alert("brand::"+brand);
		if (brand == '') {
			alert("please select brand");
			document.getElementById("brand").focus();
			return false;

		}
		
		
		//alert("settlePeriod:::"+settlePeriod);
		if (settlePeriod == '') {
			alert("please select Settle Period");
			document.getElementById("settlePeriod1").focus();
			return false;

		}
  		
  		//alert("amount::::::"+amount);
  		if (amount == '') {
  			alert("please fill amount");
  			document.getElementById("amount").focus();
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
  				    	 
  				    	  alert("please enter proper amount");
  				    	  document.getElementById("amount").focus();
  				    	   return false;
  				       }
  			
  		}
  		
  		//alert("installment::"+installment);
  		if (installment <= 0) {
			alert("please select Installement greater than Zero ");
			document.getElementById("settlePeriod1").focus();
			return false;

		}
  		
  	}
	
		return true;

	}


$(function(){
  // bind change event to select
  $('#merchantName').on('change', function () {
      var url = $(this).val(); // get selected value
      //alert(url);
      if (url) { // require a URL
          window.location = url; // redirect
         // alert(window.location);
      }
      return false;
  });
});

function addrow(){
	   var payLater = document.getElementById('payCheck').value;
		if( payLater == 'No') {
			//alert("payLater:::::"+payLater);
	        document.getElementById('toPay').style.display='none';
	  	}else if ( payLater == 'Yes'){
	  		//alert("payLater:::::"+payLater);
	  		document.getElementById('toPay').style.display='block'
	  	}else{
	  		 document.getElementById('toPay').style.display='none';
	  	}
		
	}
</script> 


</head>





<body onload="addrow();">
	<div class="container-fluid">  
<div class="row">
  <div class="col s12">
      <div class="card blue-bg text-white">
        <div class="card-content">
          <div class="d-flex align-items-center">
           <h3 class="text-white">  <strong>Update MDR details </strong></h3>
          </div>
          
          
        </div>
      </div>
    </div>
    </div>
	
				<form:form method="POST" id="form1" action="${pageContext.request.contextPath}/MDR/changeMDRDetailsReview"
			name="form1" commandName="mobileUser">	
<div class="row">
 <input type="hidden" name="payCheck" id="payCheck" value="${mdrObj.payLater}">
  <div class="col s12">
      <div class="card border-radius">
        <div class="card-content padding-card">
		 <div class="d-flex align-items-center">
				           <h5> MDR Details</h5>
				          </div>
			
			<div class="row">
					<div class="input-field col s12 m6 l6 ">
								<label>MID</label>
								<input type="text" id="mid" value="${mdrObj.mid}"
								placeholder="mid" name="mid"  path="mid" readonly />
									</div>
							
								
					<div class="input-field col s12 m6 l6 ">
								<label>Brand</label>
								<input type="text" id="brand" value="${mdrObj.cardBrand}"
								placeholder="brand" name="brand"  path="brand" readonly/>
									</div>
				</div>
								
				<div class="d-flex align-items-center">
				           <h5> Domestic MDR</h5>
				          </div>
								
					<div class="row"> 
						<div class="input-field col s12 m6 l6 ">
							<h6>CREDIT CARD</h6>
						</div>
						<div class="input-field col s12 m6 l6 ">
							<h6>DEBIT CARD</h6>
						</div>
					</div>
					<div class="row"> 
						<div class="input-field col s3 ">
								<label for="MerchantMDR">Merchant MDR</label>
								<input type="number" step=".01" id="domcreditmerchantMDR" placeholder="Merchant MDR"
								name="domcreditmerchantMDR" path="domcreditmerchantMDR" value="0.03" onchange="changeStyle(this);"/>
							</div>
						
						<div class="input-field col s3 "> 
								<label>Host MDR</label>
								
								<input type="number" step=".01" id="domcredithostMDR" placeholder="Host MDR"
								name="domcredithostMDR" path="domcredithostMDR" value="0.03" onchange="changeStyle(this);"/>
						</div>
						
						
						<div class="input-field col s3 "> 
								<label>Merchant MDR</label>

								<input type="number" step=".01"  id="domdebitmerchantMDR" placeholder="Merchant MDR"
								name="domdebitmerchantMDR" path="domdebitmerchantMDR" value="0.03" onchange="changeStyle(this);"/> 
							</div>
						
						<div class="input-field col s3 "> 
								<label>Host MDR</label>
			
								<input type="number" step=".01" id="domdebithostMDR" placeholder="Host MDR"
								name="domdebithostMDR" path="domdebithostMDR" value="0.03" onchange="changeStyle(this);"/> 
							</div>
					
						
				</div>
				<div class="d-flex align-items-center">
				           <h6> Foreign MDR</h6>
				 </div>
				
					<div class="row"> 
						<div class="input-field col s12 m6 l6 ">
							<h6>CREDIT CARD</h6>
						</div>
						<div class="input-field col s12 m6 l6 ">
							<h6>DEBIT CARD</h6>
						</div>
					</div>
					<div class="row"> 
						<div class="input-field col s3 ">
								<label>Merchant MDR</label>
								
								<input type="number" step=".01" id="focreditmerchantMDR" placeholder="Merchant MDR"
								name="focreditmerchantMDR" path="focreditmerchantMDR" value="0.03" onchange="changeStyle(this);"/>
							</div>
						
						<div class="input-field col s3 ">
								<label>Host MDR</label>
								
								<input type="number" step=".01" id="focredithostMDR" placeholder="Host MDR"
								name="focredithostMDR" path="focredithostMDR" value="0.03" onchange="changeStyle(this);"/>
							</div>
						
						
						<div class="input-field col s3 ">
								<label>Merchant MDR</label>
								
								<input type="number" step=".01" id="fodebitmerchantMDR" placeholder="Merchant MDR"
								name="fodebitmerchantMDR" path="fodebitmerchantMDR" value="0.03" onchange="changeStyle(this);"/> 
							</div>
					
						<div class="input-field col s3 ">
								<label>Host MDR</label>
								
								<input type="number" step=".01"  id="fodebithostMDR" placeholder="Host MDR"
								name="fodebithostMDR" path="fodebithostMDR" value="0.03" onchange="changeStyle(this);"/>
							</div>
						</div>
						
						<div class="d-flex align-items-center">
				           <h6> Additional Details</h6>
				 		</div>
				 		
				 		<div class="row">
							<div class="input-field col s12 m6 l6 ">
									 <input type="hidden"
										name="settlePeriod" id="settlePeriod" value="${mdrObj.settlePeriod}"> <select
										name="settlePeriod1" id="settlePeriod1"
										onchange="return loadDropDate22();"
										style="width: 100%; margin-top: 25px;">
										<option selected value="${mdrObj.settlePeriod}">${mdrObj.settlePeriod}</option>
										<option value="1">1</option>
										<option value="2">2</option>
										<option value="3">3</option>
										<option value="4">4</option>
										<option value="5">5</option>
									</select>
									<label>Settle Period</label>
								</div>
							<div class="input-field col s12 m6 l6 ">Pay Later :
								<c:if test="${mdrObj.payLater == 'No' }">
			
						<p><label> <input type="radio" name="payLater" onclick="checkPay();" path="payLater"
							value="Yes" id="payLater" /><span>Yes</span></label>   
						<label><input type="radio" checked="checked"
						name="payLater" value="No"  id="payLater1" onclick="checkPay();" path="payLater"/> <span>No</span></label></p>
									</c:if>
									<c:if test="${mdrObj.payLater == 'Yes' }">
									<p><label> <input type="radio"   checked="checked" name="payLater" onclick="checkPay();" path="payLater"
							value="Yes" id="payLater" /><span>Yes</span></label>   
						<label><input type="radio"
						name="payLater" value="No"  id="payLater1" onclick="checkPay();" path="payLater"/> <span>No</span></label></p>
									</c:if>
									</div>
								
							

							<div id="toPay" style="display: none;">
								<div class="input-field col s12 m6 l6 ">
										<label>Amount</label>
										
										<input type="text"  id="amount"
											placeholder="Amount" name="amount" path="amount" value="${mdrObj.amount}"
											onchange="changeStyle(this);" />
									</div>
								<div class="input-field col s12 m6 l6 ">
										<label>Installment</label> 
										<!-- <input type ="text" class="form-control" placeholder="Host MDR" > -->
										<input type="number" step="1" 
											id="installment" placeholder="Number of month"
											name="installment" path="installment" value="${mdrObj.installment}"
											onchange="changeStyle(this);" />
									</div>
								</div>
							</div>
						
						
						
						<div class="row">
						<div class="input-field col s12 m3 l3">
					  <div class="button-class">
						
						<button class="btn btn-primary blue-btn" type="submit" onclick="return loadSelectData();">Submit</button>
	<input type="button"  class="btn btn-primary blue-btn"  onclick="load1()" value="Cancel">
	</div></div></div></div></div></div></div>
	
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
	
						</form:form>	
						</div>
	
		
				</body>		
						
