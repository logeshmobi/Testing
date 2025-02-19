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



function load1(){
	var url = "${pageContext.request.contextPath}/MDR/addMDRDetails";
	$(location).attr('href',url);
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
		
		
		//alert("settlePeriod:::"+settlePeriod);
		if (settlePeriod == '') {
			alert("please select Settle Period");
			document.getElementById("settlePeriod").focus();
			return false;

		}
		
		
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
			document.getElementById("settlePeriod").focus();
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
  		if (installment == '0') {
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

   
</script>
<script>
function mainDet(){
	alert("maindet");
	var businessName=document.getElementById('businessName').value;
	alert("maindet--"+businessName+"---");
	var mainDiv=document.getElementById('mainDet');
	var mainDivt=document.getElementById('mainDett');
	if(businessName!=null && businessName!=''){
		alert("maindet1--"+businessName+"---");
		mainDiv.style.display='block';
		mainDivt.style.display='block';
		}else{
			alert("maindet0--"+businessName+"---");
			mainDiv.style.display='none';
			mainDivt.style.display='none';
		}
}
</script> 


</head>





<body onclick="document.getElementById('emtpyTID').style.display='none';">
	
	<div class="container-fluid">  
	<div class="row">
  <div class="col s12">
      <div class="card blue-bg text-white">
        <div class="card-content">
          <div class="d-flex align-items-center">
           <h3 class="text-white">  <strong>MDR details Review </strong></h3>
          </div>
          
          
        </div>
      </div>
    </div>
    </div>
<form:form method="POST" id="form1" action="${pageContext.request.contextPath}/MDR/addMDRDetailsConfirm"
			name="form1" commandName="mobileUser">	
			
			<div class="row">
  <div class="col s12">
      <div class="card border-radius">
        <div class="card-content padding-card">
		 <div class="d-flex align-items-center">
				           <h5> MDR Details</h5>
				          </div>
					<div class="row">
					<div class="input-field col s12 m6 l6 ">
								<label>MID</label>
								<input type="text" id="mid" value="${mobileUser.mid}"
								placeholder="mid" name="mid"  path="mid" readonly="readonly"/>
									</div>
								
								
					<div class="input-field col s12 m6 l6 ">
								<label>Brand</label>
								<input type="text" id="brand" value="${mobileUser.brand}"
								placeholder="brand" name="brand"  path="brand" readonly="readonly"/>
									</div>
					</div>
				
				                           <c:if  test="${mobileUser.brand.equalsIgnoreCase('VISA')}">
                           <div id="EWALLET">
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
                                                     <label>Merchant MDR</label>
                                                     <input type="number"  step=".01" id="domCreditMerchantMDR" value="${mobileUser.domCreditMerchantMDR}"
                                                      placeholder="domCreditMerchantMDR" name="domCreditMerchantMDR"  path="domCreditMerchantMDR" readonly="readonly"/>
                                                     </div>
                                        <div class="input-field col s3 "> 
                                                     <label>Host MDR</label>
                                                     <input type="number"  step=".01" id="domCreditHostMDR" value="${mobileUser.domCreditHostMDR}"
                                                      placeholder="domCreditHostMDR" name="domCreditHostMDR"  path="domCreditHostMDR" readonly="readonly"/>
                                                     </div>
                                        <div class="input-field col s3 "> 
                                                     <label>Merchant MDR</label>
                                                     <input type="number"  step=".01" id="domDebitMerchantMDR" value="${mobileUser.domDebitMerchantMDR}"
                                                      placeholder="domDebitMerchantMDR" name="domDebitMerchantMDR"  path="domDebitMerchantMDR" readonly="readonly"/>
                                                     </div>
                                               <div class="input-field col s3 "> 
                                                     <label>Host MDR</label>
                                                     <input type="number"  step=".01" id="domDebitHostMDR" value="${mobileUser.domDebitHostMDR}"
                                                      placeholder="domDebitHostMDR" name="domDebitHostMDR"  path="domDebitHostMDR" readonly="readonly"/>
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
                                                     <input type="number"  step=".01" id="foCreditMerchantMDR" value="${mobileUser.foCreditMerchantMDR}"
                                                      placeholder="foCreditMerchantMDR" name="foCreditMerchantMDR"  path="foCreditMerchantMDR" readonly="readonly"/>
                                               </div>
                                        
                                        
                                        <div class="input-field col s3 ">
                                                     <label>Host MDR</label>
                                                     <input type="number"  step=".01" id="foCreditHostMDR" value="${mobileUser.foCreditHostMDR}"
                                                      placeholder="foCreditHostMDR" name="foCreditHostMDR"  path="foCreditHostMDR" readonly="readonly"/>
                                               </div>
                                        
                                        
                                        
                                        <div class="input-field col s3 ">
                                                     <label>Merchant MDR</label>
                                                     <input type="number"  step=".01" id="foDebitMerchantMDR" value="${mobileUser.foDebitMerchantMDR}"
                                                      placeholder="foDebitMerchantMDR" name="foDebitMerchantMDR"  path="foDebitMerchantMDR" readonly="readonly"/>
                                               </div>
                                        
                                        
                                        <div class="input-field col s3 ">
                                                     <label>Host MDR</label>
                                                     <input type="number"  step=".01"  id="foDebitHostMDR" value="${mobileUser.foDebitHostMDR}"
                                                      placeholder="foDebitHostMDR" name="foDebitHostMDR"  path="foDebitHostMDR" readonly="readonly"/>
                                               </div>
                                        </div>
                           
                                        
                           <div id="BOOST">
                           <div class="d-flex align-items-center">
                                      <h5> BOOST  </h5>
                           </div>
                                 <div class="row"> 
                                        <div class="input-field col s12 m6 l6 ">
                                               <h6>BOOST ECOM </h6>
                                        </div>
                                        <div class="input-field col s12 m6 l6 ">
                                               <h6>BOOST QR</h6>
                                        </div>
                                 </div>
                    
                                 <div class="row"> 
                                        <div class="input-field col s3 ">
                                                     <label>Merchant MDR</label>
                                                     <input type="number"  step=".01" id="boostEcomMerchantMDR" value="${mobileUser.boostEcomMerchantMDR}"
                                                      placeholder="boostEcomMerchantMDR" name="boostEcomMerchantMDR"  path="boostEcomMerchantMDR" readonly="readonly"/>
                                                     </div>
                                        <div class="input-field col s3 "> 
                                                     <label>Host MDR</label>
                                                     <input type="number"  step=".01" id="boostEcomHostMDR" value="${mobileUser.boostEcomHostMDR}"
                                                      placeholder="boostEcomHostMDR" name="boostEcomHostMDR"  path="boostEcomHostMDR" readonly="readonly"/>
                                                     </div>
                                        <div class="input-field col s3 "> 
                                                     <label>Merchant MDR</label>
                                                     <input type="number"  step=".01" id="boostQrMerchantMDR" value="${mobileUser.boostQrMerchantMDR}"
                                                      placeholder="boostQrMerchantMDR" name="boostQrMerchantMDR"  path="boostQrMerchantMDR" readonly="readonly"/>
                                                     </div>
                                               <div class="input-field col s3 "> 
                                                     <label>Host MDR</label>
                                                     <input type="number"  step=".01" id="boostQrHostMDR" value="${mobileUser.boostQrHostMDR}"
                                                      placeholder="boostQrHostMDR" name="boostQrHostMDR"  path="boostQrHostMDR" readonly="readonly"/>
                                                     </div>
                           </div>
                           </div>
                           
                                        
                                        
                           <div id="GRAB">
                                        <div class="d-flex align-items-center">
                                      <h5> GRAB </h5>
                           </div>
                                 <div class="row"> 
                                        <div class="input-field col s12 m6 l6 ">
                                               <h6>GRAB ECOM </h6>
                                        </div>
                                        <div class="input-field col s12 m6 l6 ">
                                               <h6>GRAB QR</h6>
                                        </div>
                                 </div>
                    
                                 <div class="row"> 
                                        <div class="input-field col s3 ">
                                                     <label>Merchant MDR</label>
                                                     <input type="number"  step=".01" id="grabEcomMerchantMDR" value="${mobileUser.grabEcomMerchantMDR}"
                                                      placeholder="grabEcomMerchantMDR" name="grabEcomMerchantMDR"  path="grabEcomMerchantMDR" readonly="readonly"/>
                                                     </div>
                                        <div class="input-field col s3 "> 
                                                     <label>Host MDR</label>
                                                     <input type="number"  step=".01" id="grabEcomHostMDR" value="${mobileUser.grabEcomHostMDR}"
                                                      placeholder="grabEcomHostMDR" name="grabEcomHostMDR"  path="grabEcomHostMDR" readonly="readonly"/>
                                                     </div>
                                        <div class="input-field col s3 "> 
                                                     <label>Merchant MDR</label>
                                                     <input type="number"  step=".01" id="grabQrMerchantMDR" value="${mobileUser.grabQrMerchantMDR}"
                                                      placeholder="grabQrMerchantMDR" name="grabQrMerchantMDR"  path="grabQrMerchantMDR" readonly="readonly"/>
                                                     </div>
                                               <div class="input-field col s3 "> 
                                                     <label>Host MDR</label>
                                                     <input type="number"  step=".01" id="grabQrHostMDR" value="${mobileUser.grabQrHostMDR}"
                                                      placeholder="grabQrHostMDR" name="grabQrHostMDR"  path="grabQrHostMDR" readonly="readonly"/>
                                                     </div>
                           </div>
                           </div>
                           
                           <div class="d-flex align-items-center">
                                      <h5> TNG</h5>
                           </div>
                                 <div class="row"> 
                                        <div class="input-field col s12 m6 l6 ">
                                               <h6>TNG ECOM </h6>
                                        </div>
                                        <div class="input-field col s12 m6 l6 ">
                                               <h6>TNG QR</h6>
                                        </div>
                                 </div>
                    
                                 <div class="row"> 
                                        <div class="input-field col s3 ">
                                                     <label>Merchant MDR</label>
                                                     <input type="number"  step=".01" id="tngEcomMerchantMDR" value="${mobileUser.tngEcomMerchantMDR}"
                                                      placeholder="tngEcomMerchantMDR" name="tngEcomMerchantMDR"  path="tngEcomMerchantMDR" readonly="readonly"/>
                                                     </div>
                                        <div class="input-field col s3 "> 
                                                     <label>Host MDR</label>
                                                     <input type="number"  step=".01" id="tngEcomHostMDR" value="${mobileUser.tngEcomHostMDR}"
                                                      placeholder="tngEcomHostMDR" name="tngEcomHostMDR"  path="tngEcomHostMDR" readonly="readonly"/>
                                                     </div>
                                        <div class="input-field col s3 "> 
                                                     <label>Merchant MDR</label>
                                                     <input type="number"  step=".01" id="tngQrMerchantMDR" value="${mobileUser.tngQrMerchantMDR}"
                                                      placeholder="tngQrMerchantMDR" name="tngQrMerchantMDR"  path="tngQrMerchantMDR" readonly="readonly"/>
                                                     </div>
                                               <div class="input-field col s3 "> 
                                                     <label>Host MDR</label>
                                                     <input type="number"  step=".01" id="tngQrHostMDR" value="${mobileUser.tngQrHostMDR}"
                                                      placeholder=tngQrHostMDR name="tngQrHostMDR"  path="tngQrHostMDR" readonly="readonly"/>
                                                     </div>
                                               </div>
                                        </div>
                                        
                                        
                           
                           <div id="FPX">
                           <div class="d-flex align-items-center">
                                      <h5> FPX</h5>
                           </div>
                           <div class="row"> 
                                        <div class="input-field col s12 m6 l6 ">
                                               <h6>FPX  </h6>
                                        </div>
                                        
                                 </div>
                                 <div class="row"> 
                                        <div class="input-field col s3 ">
                                                     <label>Merchant MDR</label>
                                                     <input type="number"  step=".01" id="fpxMerchantMDR" value="${mobileUser.fpxMerchantMDR}"
                                                      placeholder="fpxMerchantMDR" name="fpxMerchantMDR"  path="fpxMerchantMDR" readonly="readonly"/>
                                                     </div>
                                        <div class="input-field col s3 "> 
                                                     <label>Host MDR</label>
                                                     <input type="number"  step=".01" id="fpxHostMDR" value="${mobileUser.fpxHostMDR}"
                                                      placeholder="fpxHostMDR" name="fpxHostMDR"  path="fpxHostMDR" readonly="readonly"/>
                                                     </div>
                                </div>
                           </div>
                           
                    
                           
                           
                           
                           
                                 </c:if>      
                           
                           
                           
                           
                           
                           
                           <c:if test="${mobileUser.brand== 'MASTERCARD'||mobileUser.brand=='UNIONPAY'}">
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
                                                     <label>Merchant MDR</label>
                                                     <input type="number"  step=".01" id="domCreditMerchantMDR" value="${mobileUser.domCreditMerchantMDR}"
                                                      placeholder="domCreditMerchantMDR" name="domCreditMerchantMDR"  path="domCreditMerchantMDR" readonly="readonly"/>
                                                     </div>
                                        <div class="input-field col s3 "> 
                                                     <label>Host MDR</label>
                                                     <input type="number"  step=".01" id="domCreditHostMDR" value="${mobileUser.domCreditHostMDR}"
                                                      placeholder="domCreditHostMDR" name="domCreditHostMDR"  path="domCreditHostMDR" readonly="readonly"/>
                                                     </div>
                                        <div class="input-field col s3 "> 
                                                     <label>Merchant MDR</label>
                                                     <input type="number"  step=".01" id="domDebitMerchantMDR" value="${mobileUser.domDebitMerchantMDR}"
                                                      placeholder="domDebitMerchantMDR" name="domDebitMerchantMDR"  path="domDebitMerchantMDR" readonly="readonly"/>
                                                     </div>
                                               <div class="input-field col s3 "> 
                                                     <label>Host MDR</label>
                                                     <input type="number"  step=".01" id="domDebitHostMDR" value="${mobileUser.domDebitHostMDR}"
                                                      placeholder="domDebitHostMDR" name="domDebitHostMDR"  path="domDebitHostMDR" readonly="readonly"/>
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
                                                     <input type="number"  step=".01" id="foCreditMerchantMDR" value="${mobileUser.foCreditMerchantMDR}"
                                                      placeholder="foCreditMerchantMDR" name="foCreditMerchantMDR"  path="foCreditMerchantMDR" readonly="readonly"/>
                                               </div>
                                        
                                        
                                        <div class="input-field col s3 ">
                                                     <label>Host MDR</label>
                                                     <input type="number"  step=".01" id="foCreditHostMDR" value="${mobileUser.foCreditHostMDR}"
                                                      placeholder="foCreditHostMDR" name="foCreditHostMDR"  path="foCreditHostMDR" readonly="readonly"/>
                                               </div>
                                        
                                        
                                        
                                        <div class="input-field col s3 ">
                                                     <label>Merchant MDR</label>
                                                     <input type="number"  step=".01" id="foDebitMerchantMDR" value="${mobileUser.foDebitMerchantMDR}"
                                                      placeholder="foDebitMerchantMDR" name="foDebitMerchantMDR"  path="foDebitMerchantMDR" readonly="readonly"/>
                                               </div>
                                        
                                        
                                        <div class="input-field col s3 ">
                                                     <label>Host MDR</label>
                                                     <input type="number"  step=".01"  id="foDebitHostMDR" value="${mobileUser.foDebitHostMDR}"
                                                      placeholder="foDebitHostMDR" name="foDebitHostMDR"  path="foDebitHostMDR" readonly="readonly"/>
                                        </div>
                                        </div>
                                        </c:if>
				
						
						<div class="d-flex align-items-center">
				           <h6>MDR Rates</h6>
				 		</div>
						<div class="row">
						<div class="input-field col s12 m6 l6 ">
						<label>Rate Id</label>
								<input type="text"  id="rateId" value="${mobileUser.rateId}"
								placeholder="rateId" name="rateId"  path="rateId" readonly="readonly"/>
							</div></div>
						
						
						<div class="d-flex align-items-center">
				           <h6> Additional Details</h6>
				 		</div>
						<div class="row">
							<div class="input-field col s12 m6 l6 ">
									<label>Settle Period</label> <input type="text"
										 id="settlePeriod"
										value="${mobileUser.settlePeriod}" placeholder="settlePeriod"
										name="settlePeriod" path="settlePeriod" readonly="readonly" />
								</div>
							<div class="input-field col s12 m6 l6 ">
									<label>Pay Later</label> <input type="text"
										 id="payLater"
										" value="${mobileUser.payLater}" placeholder="payLater"
										name="payLater" path="payLater" readonly="readonly" />
								</div>
							

							<c:if test="${mobileUser.payLater == 'Yes' }">
								<div class="input-field col s12 m6 l6 ">
										<label>Amount</label> <input type="text"
											id="amount" " value="${mobileUser.amount}"
											placeholder="amount" name="amount" path="amount"
											readonly="readonly" />
									</div>
								<div class="input-field col s12 m6 l6 ">
										<label>Installment</label> <input type="number"
											 id="installment"
											value="${mobileUser.installment}" placeholder="installment"
											name="installment" path="installment" readonly="readonly" />
									</div>
								
							</c:if>
							</div>
						<div class="row">
						<div class="input-field col s12 m6 l6 ">
						<div class="button-class"  style="float:left;">	
						<button class="btn btn-primary blue-btn" type="submit"	onclick="return loadSelectData();">Submit</button>
						<input type="button"  class="btn btn-primary blue-btn"  onclick="load1()" value="Cancel">
						</div></div></div>
				</div></div></div></div>
	</form:form>	
						</div>
	
				</body>		
						
