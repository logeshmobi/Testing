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
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>MDR</title>   



<script lang="JavaScript">

function loadDropDate22() {
	//alert("strUser.value");
	var e = document.getElementById("settlePeriod1");

	var strUser = e.options[e.selectedIndex].value;
	document.getElementById("settlePeriod").value = strUser;
	//alert("data :" + strUser + " "+ document.getElementById("status1").value);

}


function loadDropDate11() {
var e = document.getElementById("brand1");
    
    brand  = $("#brand").val();
    if(brand =='MASTERCARD' || brand =='UNIONPAY'){
       //alert("card");
         $("#CARD").show();
         $("#EWALLET").hide();
         
      //   $("#EWALLET").removeAttr("disabled");



       
    }
    if(brand=='VISA')
    {
        //alert("wallet");
        $("#CARD").hide();
         $("#EWALLET").show();
      //   $("#CARD").removeAttr("disabled");
    }
    



   var strUser = e.options[e.selectedIndex].value;
    document.getElementById("brand").value = strUser;

}

function loadDropDate() {
	// alert("strUser.value"); 
	var e = document.getElementById("mid1");

	var strUser = e.options[e.selectedIndex].value;
	document.getElementById("mid").value = strUser;
	//alert("data :" + strUser + " "+ document.getElementById("tid1").value);
 
}

/* function loadDropDate12() {
	//alert("strUser.value");
	var e = document.getElementById("cardtype");

	var strUser = e.options[e.selectedIndex].value;
	document.getElementById("cardtype1").value = strUser;
	//alert("data :" + strUser + " "+ document.getElementById("status1").value);

} */

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
			document.getElementById("mid1").focus();
			return false;

		}
		
		//alert("brand::"+brand);
		if (brand == '') {
			alert("please select brand");
			document.getElementById("brand1").focus();
			return false;

		}
		
		
		//alert("settlePeriod:::"+settlePeriod);
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
			document.getElementById("mid1").focus();
			return false;

		}
		
		//alert("brand::"+brand);
		if (brand == '') {
			alert("please select brand");
			document.getElementById("brand1").focus();
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
  $('#merchantName'). on('change', function () {
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
           <h3 class="text-white">  <strong>Add MDR details </strong></h3>
          </div>
          
          
        </div>
      </div>
    </div>
    </div>
    
    
    <div class="row">
    <c:if test="${ responseEmptyMids!=null }">
			
  				<center>  
  					<p id="emtpyTID"> <h3 style="color:red;">* Empty MID Details.. Please Fill Anyone MID Details...</h3></p></center>
 			</c:if>
 			<c:if test="${responseEmptyDeviceID!=null }">
			
  				<center>  
  					<p id="emtpyDeviceID"> <h3 style="color:red;">* Empty DeviceID Details..</h3></p></center>
 			</c:if>
 			
  <div class="col s12">
      <div class="card border-radius">
        <div class="card-content padding-card">
        	<div class="row">
							<div class="input-field col s12 m3 l3">
								
									Business Name
									
							</div>	
							
							<div class="input-field col s12 m5 l5">
					   <select   id="merchantName" path="merchantName"
					    class="browser-default select-filter">
   
            <optgroup label="Business Names" > 
								<option selected value=""><c:out value="business Name"/></option>
								
								<c:forEach items="${merchant1}" var="merchant1">
									<option value="${pageContext.request.contextPath}/MDR/findMIDDetails?id=${merchant1.id}">
										${merchant1.businessName}~${merchant1.username}~${merchant1.role}
									
									</option>

								</c:forEach>
								</optgroup> 
        </select>   
						</div>	
						
						<div class="input-field col s12 m5 l5">
						<c:if test="${responseErrorData  != null}">
							<H4 style="color: #ff4000;" align="center">${responseErrorData}</H4>
						</c:if>
						</div>
							
							</div>
					
					<div class="row">
					 <div class="input-field col s12 m3 l3"></div>

        <!-- Script -->
        <script>
        $(document).ready(function(){
            
       
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
.select2-results__options li{
	list-style-type: none;												
	padding: 4px 0px 4px 4px !important;
	font-size: 18px !important;
}
ul.select2-results__options li:hover{
	/* overflow:scroll;   */
}
ul.select2-results__options li{
	max-height:250px;
	/* overflow:auto;   */
	curser:pointer;
 }
li ul .select2-results__option:hover{
	background-color: #005baa !important;
	/* overflow: auto; */
	color:#fff !important; 
}
.select-search-hidden .select2-container{
	display:none !important;
} 

</style>


												
							<input type="text" class="shownSearch" id="businessName" readonly
								Placeholder="businessName" value="${mobileUser.businessName }"/>
							</div>
							<div class="cover-bar"></div>
							
						</div>	</div>
						</div></div></div>
	<form:form method="POST" id="form1" action="${pageContext.request.contextPath}/MDR/regMDRDetails" name="form1" commandName="mobileUser">	
<input type="hidden" path="businessName" id="businessName" readonly
			name="businessName" Placeholder="businessName" value="${mobileUser.businessName }"/>
<c:if test="${mobileUser.businessName !=null }">
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
					</div>
					<div class="input-field col s12 m6 l6 ">
						<label>Brand</label> 
						</div>
					<div class="input-field col s12 m6 l6 select-search-hidden drop-details ">
						
					<input type="hidden" name="mid" id="mid" value="${mid}">
					
					<select name="mid1"  style="width:100%"  id="mid1"
							 onchange="document.getElementById('mid').value=document.getElementById('mid1').value;" >
						<!-- <optgroup label="MID" style="width:100%"> -->
						<option selected value=""><c:out value="MID" /></option>
						<c:forEach items="${midList}" var="mid">
						<option value="${mid}">${mid}</option>
						</c:forEach>
						</select>				
						
						</div>
						
						<div class="input-field col s12 m6 l6 select-search-hidden drop-details">
						<input type="hidden" name="brand1"
						id="brand1" value="${brand}">
						
						 <select
						name="brand" id="brand" onchange="return loadDropDate11();"
						 >
						<option selected value="" id ="choose">Choose</option>
						<option value="VISA">VISA</option>
						<option value="MASTERCARD">MASTERCARD</option>
						<option value="UNIONPAY">UNIONPAY</option>												
						</select>
						
						</div>
					</div> 
					
					<div id="CARD" style="display:none;">
                                 <div>
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
             
                                 <div class="row"> 
                                        <div class="input-field col s3 m3 l3 ">
                                                     <label for="MerchantMDR">Merchant MDR</label>
                                                     <input type="number" step=".01" id="domcreditmerchantMDR" placeholder="Merchant MDR"
                                                      name="domcreditmerchantMDR" path="domcreditmerchantMDR" value="0.03" onchange="changeStyle(this);"/>
                                               </div>
                                        
                                        <div class="input-field col s3 m3 l3 "> 
                                                     <label>Host MDR</label>
                                                     
                                                     <input type="number" step=".01" id="domcredithostMDR" placeholder="Host MDR"
                                                     name="domcredithostMDR" path="domcredithostMDR" value="0.03" onchange="changeStyle(this);"/>
                                        </div>
                                        
                                        
                                        <div class="input-field col s3 m3 l3 "> 
                                                     <label>Merchant MDR</label>

                                                     <input type="number" step=".01"  id="domdebitmerchantMDR" placeholder="Merchant MDR"
                                                      name="domdebitmerchantMDR" path="domdebitmerchantMDR" value="0.03" onchange="changeStyle(this);"/> 
                                               </div>
                                        
                                        <div class="input-field col s3 m3 l3 "> 
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
                                        <div class="input-field col s3 m3 l3 ">
                                                     <label>Merchant MDR</label>
                                                     
                                                     <input type="number" step=".01" id="focreditmerchantMDR" placeholder="Merchant MDR"
                                                      name="focreditmerchantMDR" path="focreditmerchantMDR" value="0.03" onchange="changeStyle(this);"/>
                                               </div>
                                        
                                        <div class="input-field col s3 m3 l3 ">
                                                     <label>Host MDR</label>
                                                     
                                                     <input type="number" step=".01" id="focredithostMDR" placeholder="Host MDR"
                                                     name="focredithostMDR" path="focredithostMDR" value="0.03" onchange="changeStyle(this);"/>
                                               </div>
                                        
                                        
                                        <div class="input-field col s3 m3 l3 ">
                                                     <label>Merchant MDR</label>
                                                     
                                                     <input type="number" step=".01" id="fodebitmerchantMDR" placeholder="Merchant MDR"
                                                      name="fodebitmerchantMDR" path="fodebitmerchantMDR" value="0.03" onchange="changeStyle(this);"/> 
                                               </div>
                                 
                                        <div class="input-field col s3 m3 l3 ">
                                                     <label>Host MDR</label>
                                                     
                                                     <input type="number" step=".01"  id="fodebithostMDR" placeholder="Host MDR"
                                                     name="fodebithostMDR" path="fodebithostMDR" value="0.03" onchange="changeStyle(this);"/>
                                               </div>
                                        </div>
                           </div>       
                    </div>
                    </div>
                    <div id="EWALLET" style="display:none;">
                    
                    
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
             
                                 <div class="row"> 
                                        <div class="input-field col s3 m3 l3 ">
                                                     <label for="MerchantMDR">Merchant MDR</label>
                                                     <input type="number" step=".01" id="domcreditmerchantMDR" placeholder="Merchant MDR"
                                                      name="domcreditmerchantMDR1" path="domcreditmerchantMDR1" value="0.03" onchange="changeStyle(this);"/>
                                               </div>
                                        
                                        <div class="input-field col s3 m3 l3 "> 
                                                     <label>Host MDR</label>
                                                     
                                                     <input type="number" step=".01" id="domcredithostMDR" placeholder="Host MDR"
                                                      name="domcredithostMDR1" path="domcredithostMDR1" value="0.03" onchange="changeStyle(this);"/>
                                        </div>
                                        
                                        
                                        <div class="input-field col s3 m3 l3 "> 
                                                     <label>Merchant MDR</label>

                                                     <input type="number" step=".01"  id="domdebitmerchantMDR" placeholder="Merchant MDR"
                                                      name="domdebitmerchantMDR1" path="domdebitmerchantMDR1" value="0.03" onchange="changeStyle(this);"/> 
                                               </div>
                                        
                                        <div class="input-field col s3 m3 l3 "> 
                                                     <label>Host MDR</label>
                    
                                                     <input type="number" step=".01" id="domdebithostMDR" placeholder="Host MDR"
                                                     name="domdebithostMDR1" path="domdebithostMDR1" value="0.03" onchange="changeStyle(this);"/> 
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
                                        <div class="input-field col s3 m3 l3 ">
                                                     <label>Merchant MDR</label>
                                                     
                                                     <input type="number" step=".01" id="focreditmerchantMDR" placeholder="Merchant MDR"
                                                      name="focreditmerchantMDR1" path="focreditmerchantMDR1" value="0.03" onchange="changeStyle(this);"/>
                                               </div>
                                        
                                        <div class="input-field col s3 m3 l3 ">
                                                     <label>Host MDR</label>
                                                     
                                                     <input type="number" step=".01" id="focredithostMDR" placeholder="Host MDR"
                                                     name="focredithostMDR1" path="focredithostMDR1" value="0.03" onchange="changeStyle(this);"/>
                                               </div>
                                        
                                        
                                        <div class="input-field col s3 m3 l3 ">
                                                     <label>Merchant MDR</label>
                                                     
                                                     <input type="number" step=".01" id="fodebitmerchantMDR" placeholder="Merchant MDR"
                                                      name="fodebitmerchantMDR1" path="fodebitmerchantMDR1" value="0.03" onchange="changeStyle(this);"/> 
                                               </div>
                                 
                                        <div class="input-field col s3 m3 l3 ">
                                                     <label>Host MDR</label>
                                                     
                                                     <input type="number" step=".01"  id="fodebithostMDR" placeholder="Host MDR"
                                                     name="fodebithostMDR1" path="fodebithostMDR1" value="0.03" onchange="changeStyle(this);"/>
                                               </div>
                                        </div>
                                 
                    
                    
                    <div class="row">
                        <div class="input-field col s12 m6 l6 ">
                            <h6>GRAB ECOM</h6>
                        </div>
                        <div class="input-field col s12 m6 l6 ">
                            <h6>GRAB QR</h6>
                        </div>
                    </div>
                    
                        <div class="input-field col s3 m3 l3 ">
                        <label>Merchant MDR</label>
                        <input type="number" step=".01" id="grabEcommerchantMDR" placeholder="Merchant MDR"
                                name="grabEcommerchantMDR" path="grabEcommerchantMDR" value="0.03" onchange="changeStyle(this);"/>
                        </div>
                        <div class="input-field col s3 m3 l3 ">
                        <label>Host MDR</label>
                        <input type="number" step=".01" id="grabEcomhostMDR" placeholder="Merchant MDR"
                                name="grabEcomhostMDR" path="grabEcomhostMDR" value="0.03" onchange="changeStyle(this);"/>
                        </div>
                        <div class="input-field col s3 m3 l3 ">
                                <label>Merchant MDR</label>
                                
                                <input type="number" step=".01" id="grabQrmerchantMDR" placeholder="Merchant MDR"
                                name="grabQrmerchantMDR" path="grabQrmerchantMDR" value="0.03" onchange="changeStyle(this);"/>
                            </div>
                    
                        <div class="input-field col s3 m3 l3 ">
                                <label>Host MDR</label>
                                
                                <input type="number" step=".01"  id="grabQrHostMDR" placeholder="Host MDR"
                                name="grabQrHostMDR" path="grabQrHostMDR" value="0.03" onchange="changeStyle(this);"/>
                        </div>
                      
                           
            
           
                    
                    
                    
                                 <div class="row"> 
                                        <div class="input-field col s12 m6 l6 ">
                                               <h6>BOOST ECOM</h6>
                                        </div>
                                        <div class="input-field col s12 m6 l6 ">
                                               <h6>BOOST QR</h6>
                                        </div>
                                 </div>
                           
                                        <div class="input-field col s3 m3 l3 ">
                                        <label>Merchant MDR</label>
                                        <input type="number" step=".01" id="boostEcommerchantMDR" placeholder="Merchant MDR"
                                                      name="boostEcommerchantMDR" path="boostEcommerchantMDR" value="0.03" onchange="changeStyle(this);"/> 
                                        </div>
                                        <div class="input-field col s3 m3 l3 ">
                                        <label>Host MDR</label>
                                        <input type="number" step=".01" id="boostEcomhostMDR" placeholder="Merchant MDR"
                                                     name="boostEcomhostMDR" path="boostEcomhostMDR" value="0.03" onchange="changeStyle(this);"/> 
                                        </div>
                                        <div class="input-field col s3 m3 l3 ">
                                                     <label>Merchant MDR</label>
                                                     
                                                     <input type="number" step=".01" id="boostQrmerchantMDR" placeholder="Merchant MDR"
                                                      name="boostQrmerchantMDR" path="boostQrmerchantMDR" value="0.03" onchange="changeStyle(this);"/> 
                                               </div>
                                 
                                        <div class="input-field col s3 m3 l3 ">
                                                     <label>Host MDR</label>
                                                     
                                                     <input type="number" step=".01"  id="boostQrHostMDR" placeholder="Host MDR"
                                                     name="boostQrHostMDR" path="boostQrHostMDR" value="0.03" onchange="changeStyle(this);"/>
                                               </div>
                           
                    
                    
                    
                           
                                        
             
       
                    
                    
                    
                                 <div class="row"> 
                                        <div class="input-field col s12 m6 l6 ">
                                               <h6>TNG ECOM</h6>
                                        </div>
                                        <div class="input-field col s12 m6 l6 ">
                                               <h6>TNG QR</h6>
                                        </div>
                                 </div>
                                        <div class="input-field col s3 m3 l3 ">
                                        <label>Merchant MDR</label>
                                        <input type="number" step=".01" id="tngEcommerchantMDR" placeholder="Merchant MDR"
                                                      name="tngEcommerchantMDR" path="tngEcommerchantMDR" value="0.03" onchange="changeStyle(this);"/> 
                                        </div>
                                        <div class="input-field col s3 m3 l3 ">
                                        <label>Host MDR</label>
                                        <input type="number" step=".01" id="tngEcomhostMDR" placeholder="Merchant MDR"
                                                     name="tngEcomhostMDR" path="tngEcomhostMDR" value="0.03" onchange="changeStyle(this);"/> 
                                        </div>
                                        <div class="input-field col s3 m3 l3 ">
                                                     <label>Merchant MDR</label>
                                                     
                                                     <input type="number" step=".01" id="tngQrmerchantMDR" placeholder="Merchant MDR"
                                                     name="tngQrmerchantMDR" path="tngQrmerchantMDR" value="0.03" onchange="changeStyle(this);"/> 
                                               </div>
                                 
                                        <div class="input-field col s3 m3 l3 ">
                                                     <label>Host MDR</label>
                                                     
                                                     <input type="number" step=".01"  id="tngQrHostMDR" placeholder="Host MDR"
                                                     name="tngQrHostMDR" path="tngQrHostMDR" value="0.03" onchange="changeStyle(this);"/>
                                               </div>
                    
                    <div class="row"> 
                                        <div class="input-field col s12 m6 l6 ">
                                               <h6>FPX</h6>
                                        </div>
                                        
                                 </div>
                                        <div class="input-field col s3 m3 l3 ">
                                        <label>Merchant MDR</label>
                                        <input type="number" step=".01" id="fpxmerchantMDR" placeholder="Merchant MDR"
                                                     name="fpxmerchantMDR" path="fpxmerchantMDR" value="0.03" onchange="changeStyle(this);"/> 
                                        </div>
                                        <div class="input-field col s3 m3 l3 ">
                                        <label>Host MDR</label>
                                        <input type="number" step=".01" id="fpxhostMDR" placeholder="Merchant MDR"
                                                     name="fpxhostMDR" path="fpxhostMDR" value="0.03" onchange="changeStyle(this);"/> 
                                        </div>
                    </div>
                    </div> 
					
						
						<div class="d-flex align-items-center">
				           <h6> MDR Rates</h6>
				 		</div>
				 		
				 		<div class="row">
								<div class="input-field col s12 m6 l6 ">
								<label>Rate ID</label>
								
								<input type="text"  id="rateId" placeholder="Rate ID"
								name="rateId" path="rateId"  onchange="changeStyle(this);"/>
							</div>
						</div>
						
						<div class="d-flex align-items-center">
				           <h6> Additional Details</h6>
				 		</div>
				 		
				 		<div class="row">
								<div class="input-field col s12 m6 l6 ">
										<input type="hidden" name="settlePeriod"
											id="settlePeriod" value=""> <select name="settlePeriod1"
											id="settlePeriod1" onchange="return loadDropDate22();"
											>
											<option selected value="">Choose</option>
											<option value="1">1</option>
											<option value="2">2</option>
											<option value="3">3</option>
											<option value="4">4</option>
											<option value="5">5</option>
											<option value="6">6</option>
											<option value="7">7</option>
											<option value="8">8</option>
											<option value="9">9</option>
											<option value="10">10</option>
											<option value="Normal">Normal</option>
										</select>
										<label>Settle Period</label> 
									</div>
								
									<div class="input-field col s12 m6 l6 ">Pay Later :
						<p><label> <input type="radio" name="payLater" onclick="checkPay();" path="payLater"
							value="Yes" id="payLater" /><span>Yes</span></label>   
						<label><input type="radio" checked="checked"
						name="payLater" value="No"  id="payLater1" onclick="checkPay();" path="payLater"/> <span>No</span></label></p>
                                
                 		 
					</div>
	
								<div id="toPay" style="display:none;">
								<div class="input-field col s12 m6 l6 ">
										<label>Amount</label>
										
										<input type="text"
											id="amount" placeholder="Amount"
											name="amount" path="amount"
											value="" onchange="changeStyle(this);" />
									</div>
								
								<div class="input-field col s12 m6 l6 ">
										<label>Installment</label>
										<!-- <input type ="text" class="form-control" placeholder="Host MDR" > -->
										<input type="number" step="1" 
											id="installment" placeholder="Number of month"
											name="installment" path="installment" value="0"
											onchange="changeStyle(this);" />
									</div>
									</div>
								</div>
								
				 		
						
						 <button class="submitBtn" type="submit"  onclick="return loadSelectData();">Submit</button>		 
						
						</div>	
						</div>	
					</div></div>
						   
			<style>
			.submitBtn {padding: 8px 20px;
    border-radius: 10px;
    background-color: #54b74a;
    color: #fff;
    margin: auto;
    display: table;}
			</style>
						
					</c:if>
	
			</form:form>
</div>			
	<script type="text/javascript">
	jQuery(document).ready(function() {
		
		
		//$('#brand').select2();
		//$('#mid1').select2(); 
		//$('#merchantName').select2();
	});
</script>
</body>
</html>