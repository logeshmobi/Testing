<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@page import="com.mobiversa.payment.controller.MerchantWebController"%>
<%@page import="com.mobiversa.common.bo.Merchant"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>


<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<html lang="en-US">
<head>

<meta
	content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no"
	name="viewport">
<link href="https://cdnjs.cloudflare.com/ajax/libs/sweetalert/1.1.3/sweetalert.min.css" rel="stylesheet"/>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/2.1.1/jquery.min.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/sweetalert/1.1.3/sweetalert.min.js"></script>


<script type="text/javascript">
window.history.replaceState({}, document.title, "/MobiversaAdmin/" + "admmerchant/");

</script>

<script type="text/javascript">
  
function load()
{
	
 //$('#testing').click(function(){
	 
	
      	swal({
      		title: "Are you sure? you want to add this Merchant Details",
      		text: "it will be added..!",
      		type: "warning",
      		showCancelButton: true,
      		confirmButtonText: "Yes, add it!",
      		cancelButtonText: "No, cancel!",
      		closeOnConfirm: false,
      		closeOnCancel: false,
      	  /*  confirmButtonClass: 'btn btn-success',
      	  cancelButtonClass: 'btn btn-danger', */
      		
      	}, function(isConfirm) {
      		if (isConfirm) {
      			
       			//swal("Added!", "Your Merchant Promotion details added","success");
       			$("#form-add").submit();
      			
      			
      		} else {
      			// swal("Cancelled", "Your Merchant Promotion details not added", "error"); 
      			 var url = "${pageContext.request.contextPath}/admmerchant/list/1"; 
      			$(location).attr('href',url);
      			//return true;
      		}
      	});
     // });
 
}
</script>
<script type="text/javascript">
  
function load1()
{
	
 //$('#testing').click(function(){
	 
	
      	swal({
      		title: "Are you sure? Do you want to Cancel to add Merchant Details",
      		text: "Registration of Merchant Cancelled..!",
      		type: "warning",
      		showCancelButton: true,
      		confirmButtonText: "Yes, Don't Register!",
      		cancelButtonText: "No, Do Register!",
      		closeOnConfirm: false,
      		closeOnCancel: true,
      	  /*  confirmButtonClass: 'btn btn-success',
      	  cancelButtonClass: 'btn btn-danger', */
      		
      	}, function(isConfirm) {
      		if (isConfirm) {
      			
       			//swal("Added!", "Your Merchant Promotion details added","success");
       			var url = "${pageContext.request.contextPath}/admmerchant/addMerchant"; 
      			$(location).attr('href',url);
      			
      			
      		} 
      	});
     // });
 
}
</script>


<script type="text/javascript">

function addrow(){
	//alert("dsfdfdfdf");
	//var i =${merchant.ownerCount};
	//alert("Data :"+i);
	 var i=document.getElementById("ownerCount").value;
	//alert(i);
	//disableRow();
	//var i=ownercnt.value;
//	document.getElementById("ownercnt" +i).style.display='';
	//alert(i);
	var a=2;
	for(a=2;a<=i;a++){
		//alert(a);
		document.getElementById("owner"+a).style.display = '';
		document.getElementById("owner"+a+a).style.display = '';
	} 
}
function disableRow(){
		document.getElementById("owner2").style.display = 'none';
		document.getElementById("owner22").style.display = 'none';
		document.getElementById("owner3").style.display = 'none';
		document.getElementById("owner33").style.display = 'none';
		document.getElementById("owner4").style.display = 'none';
		document.getElementById("owner44").style.display = 'none';
		document.getElementById("owner5").style.display = 'none';
		document.getElementById("owner55").style.display = 'none';
}



</script>

</head>


<body onload="addrow()">

<form:form action="admmerchant/merchantDetailsReviewAndConfirm" method="post" 
		commandName="merchant" id="form-add" >
		<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
		<input type="hidden" name="id" value="${merchant.id}" />
	<div class="container-fluid">    
  <div class="row">
  <div class="col s12">
      <div class="card blue-bg text-white">
        <div class="card-content">
          <div class="d-flex align-items-center">
           <h3 class="text-white">  <strong> Merchant </strong>   &nbsp;&nbsp;<small>Add Merchant </small> </h3>
          </div>
          
          
        </div>
      </div>
    </div>
    </div>
    
    <div class="row">
  <div class="col s12">
      <div class="card border-radius">
        <div class="card-content padding-card">
          <div class="d-flex align-items-center">
           <h5> Merchant Details</h5>
          </div>
          
				<div class="row">
					<div class="input-field col s12 m4 l4">
						<input type="text" id="registeredName" placeholder="registeredName" name="registeredName" value="${merchant.registeredName}" disabled="disabled">
						<label for="first_name">Registered Name</label>
					</div>
					<div class="input-field col s12 m4 l4">
						<input type="text" id="businessName" placeholder="businessName" name="businessName" value="${merchant.businessName}" disabled="disabled">
						<label for="name">Business Name</label>
					</div>
					<div class="input-field col s12 m4 l4">
						<input type="text" id="businessRegNo" placeholder="businessRegNo" name="businessRegNo" value="${merchant.businessRegNo}" disabled="disabled">
						<label for="name">Business Reg No</label>
					</div>
				</div>
		</div></div></div></div>
		<div class="row">
  <div class="col s12">
      <div class="card border-radius">
        <div class="card-content padding-card">
        <div class="d-flex align-items-center">
			           <h5> Paydee Details</h5>
			         </div>
			 <div class="row">
					
					<div class="input-field col s12 m4 l4">
						 
						<input type="text" id="mid" placeholder="mid" name="mid" value="${merchant.mid}" disabled="disabled">
						<label for="name">EZYWIRE MID</label>

					</div>
					
					<div class="input-field col s12 m4 l4">
						<input type="text" id="ezymotomid"
							name="ezymotomid" value="${merchant.ezymotomid}" readonly="true">
						<label for="name">EZYMOTO MID</label>
	
					</div>
					
					<div class="input-field col s12 m4 l4">
					<input type="text" id="ezypassmid" 
							 name="ezypassmid" value="${merchant.ezypassmid}" readonly="true">
					
					<label for="name">EZYPASS MID</label>
	
					</div>
					
				</div>
				
				<div class="row">
					
					<div class="input-field col s12 m4 l4">
					<input type="text" id="ezywaymid" 
							name="ezywaymid" value="${merchant.ezywaymid}" readonly="true">
						
						<label for="name">EZYWAY MID</label>
				
					</div>
					
					<div class="input-field col s12 m4 l4">
					<input type="text" id="ezyrecmid"
							name="ezyrecmid" value="${merchant.ezyrecmid}" readonly="true">
						<label for="name">EZYREC MID</label>		
	
					</div>
					
				</div>
				
				<div class="d-flex align-items-center">
			           <h5> UMobile Details</h5>
			         </div>
			    <div class="row">
					
					<div class="input-field col s12 m4 l4">
						 
						<input type="text" id="umMid" 
						name="umMid" value="${merchant.umMid}" disabled="disabled">
						<label for="name">UM_EZYWIRE MID</label>

					</div>
					
					<div class="input-field col s12 m4 l4">
						<input type="text"  id="umMotoMid" 
							name="umMotoMid" value="${merchant.umMotoMid}" readonly="true">
						<label for="name">UM_EZYMOTO MID</label>
						
					</div>
					
					<div class="input-field col s12 m4 l4">
					<input type="text" id="umEzypassMid" 
									 name="umEzypassMid" value="${merchant.umEzypassMid}" readonly="true">
					
					<label for="name">UM_EZYPASS MID</label>
					
					
					</div>
					
				</div>
				
				<div class="row">
					
					<div class="input-field col s12 m4 l4">
					<input type="text"  id="umEzywayMid"
									name="umEzywayMid" value="${merchant.umEzywayMid}" readonly="true">
						<label for="name">UM_EZYWAY MID</label>

					</div>
					
					<div class="input-field col s12 m4 l4">
					<input type="text" id="umEzyrecMid"
							name="umEzyrecMid" value="${merchant.umEzyrecMid}" readonly="true">
						<label for="name">UM_EZYREC MID</label>		
			
					</div>
					
				</div>
	</div>
      </div>
    </div>
    </div>
	
	<div class="row">
  <div class="col s12">
      <div class="card border-radius">
        <div class="card-content padding-card">
          <div class="d-flex align-items-center">
           <h5> Address Details</h5>
          </div>
          <div class="row">
					<div class="input-field col s12 ">
						<label for="first_name">Registered Address</label>
						<textarea  row="2"  column="2" max-length="120"  id="registeredAddress" style="word-break:break-all;" name="registeredAddress"  disabled="disabled">${merchant.registeredAddress}</textarea>
					</div>
					<div class="input-field col s12 ">
						
						<label for="name">Business Address</label>
						<textarea  row="2"  column="2" max-length="120" id="businessAddress" style="word-break:break-all;" name="businessAddress"  disabled="disabled">${merchant.businessAddress}</textarea>
					</div>
					<div class="input-field col s12 ">
						<!-- <input id="name" type="text"> -->
						<label for="name">Mailing Address</label>
						<textarea  row="2"  column="2" max-length="120" id="mailingAddress" style="word-break:break-all;" name="mailingAddress"  disabled="disabled">${merchant.mailingAddress}</textarea>
					</div>
				</div>	
				<div class="row">
					<div class="input-field col s12 m4 l4">
						<!-- <input id="name" type="text" class="validate"> -->
						<label for="first_name">Business City</label>
						<input type="text" id="businessCity" placeholder="businessCity" name="businessCity" value="${merchant.businessCity}" disabled="disabled">
					</div>
					<div class="input-field col s12 m4 l4">
						
						<label for="name">Business PostalCode</label>
						<input type="text" id="businessPostCode" placeholder="businessPostCode" name="businessPostCode" value="${merchant.businessPostCode}" disabled="disabled">
					</div>
					<div class="input-field col s12 m4 l4">
							
						<label for="name">Business State</label>
						<input type="text" id="businessState" placeholder="businessState" name="businessState" value="${merchant.businessState}" disabled="disabled">
			
					</div>
				</div>
			
				
				<div class="row">
				<div class="col s12">
				   <div class="card border-radius">					
					  <div class="d-flex align-items-center">
					   <h5> Contact Person Details</h5>
					  </div> 
					  <div class="row">
						 <div class="input-field blue-input col s12 m4 l4">
						
						  <label for="first_name">Title</label>
						  <input type="text" id="salutation" placeholder="salutation" name="salutation" value="${merchant.salutation}" disabled="disabled">
						  
						</div>
						
						 <div class="input-field col s12 m8 l8">
							
							 <label for="Business_City">Contact Person Name</label>
							 <input type="text"  id="name" placeholder="name" name="name" value="${merchant.name}" disabled="disabled">
						</div> 
						</div>
					  </div>
				  </div> 
				</div>	
				
					 
				 <div class="row">
				<div class="col s12">
				   <div class="card border-radius"> 
						 <div class="input-field col s12 m5 l5">
							 
							 <label for="Email">Email</label>
							 <input type="text" id="email" placeholder="email" name="email" value="${merchant.email}" disabled="disabled">
						</div> 
						
						 <div class="input-field col s12 m4 l4">
							 
							 <label for="contact_no">Contact No</label>
							 <input type="text" id="contactNo" placeholder="contactNo" name="contactNo" value="${merchant.contactNo}" disabled="disabled">
						</div> 
				  </div> 
				</div>
				</div>	
				
				<div class="row">
				<div class="col s12">
				   <div class="card border-radius"> 
				    <div class="d-flex align-items-center">
					   <h5>Owner's/Director's Details</h5>
					  </div> 
					  <div class="row">
						  <div class="input-field blue-input col s12 m4 l4">
						  	<input type="text" path="ownerCount" 
								value="${merchant.ownerCount}" id="ownerCount" name="ownerCount" disabled="disabled">
						  </div>
					  </div>
				<div class="row">
				 <div class="input-field col s12 m4 l4">
						<label for="ownerSalutation1">Owner Salutation1</label>
						
						<input type="text"  id="ownerSalutation1" placeholder="ownerSalutation1" name="ownerSalutation1" value="${merchant.ownerSalutation1}" disabled="disabled">
					</div>

				 <div class="input-field col s12 m4 l4">
						<label for="email">Owner Name1</label> 
						<input type="text" id="ownerName1" placeholder="ownerName1" name="ownerName1" value="${merchant.ownerName1}" disabled="disabled">
					</div>

				 <div class="input-field col s12 m4 l4">
						<label for="email">Owner ContactNo1</label>
						
						<input type="text"  id="ownerContactNo1" placeholder="ownerContactNo1" name="ownerContactNo1" value="${merchant.ownerContactNo1}" disabled="disabled">
					</div>

				</div>
				
				<div class="row">
				 <div class="input-field col s12 m4 l4">
						<label for="email">NRIC Passport1</label> 
						<input type="text"  id="passportNo1" placeholder="passportNo1" name="passportNo1" value="${merchant.passportNo1}" disabled="disabled">
					</div>

				 <div class="input-field col s12 m4 l4">
						<label for="residentialAddress1">Residential Address</label>
						
						<input type="text" id="residentialAddress1" placeholder="residentialAddress1" name="residentialAddress1" value="${merchant.residentialAddress1}" disabled="disabled">
					</div>				
				</div>
				
				<div style="display: none;" id="owner2">
				<div class="row">
					 <div class="input-field col s12 m4 l4">
							<label for="ownerSalutation2">Owner Salutation2</label>
							
							<input type="text" id="ownerSalutation2" placeholder="ownerSalutation2" name="ownerSalutation2" value="${merchant.ownerSalutation2}" disabled="disabled">
						</div>

					 <div class="input-field col s12 m4 l4">							<label for="ownerName2">Owner Name2</label>
							
							<input type="text" id="ownerName2" placeholder="ownerName2" name="ownerName2" value="${merchant.ownerName2}" disabled="disabled">
						</div>

					 <div class="input-field col s12 m4 l4">
							<label for="ownerContactNo2">Owner ContactNo2</label>
							
							<input type="text"  id="ownerContactNo2" placeholder="ownerContactNo2" name="ownerContactNo2" value="${merchant.ownerContactNo2}" disabled="disabled">
						</div>

					

				</div>
			</div>

			<div style="display: none;" id="owner22">
				<div class="row">
					 <div class="input-field col s12 m4 l4">
							<label for="passportNo2">NRIC Passport2</label>
							
							<input type="text"  id="passportNo2" placeholder="passportNo2" name="passportNo2" value="${merchant.passportNo2}" disabled="disabled">
						</div>

					 <div class="input-field col s12 m4 l4">
							<label for="email">Residential Address2</label>
							
							<input type="text" id="residentialAddress2" placeholder="residentialAddress2" name="residentialAddress2" value="${merchant.residentialAddress2}" disabled="disabled">
						</div>

					
				</div>
			</div>
			<!-- -owner details3 -->

			<div style="display: none;" id="owner3">
				<div class="row">
					 <div class="input-field col s12 m4 l4">
							<label for="email">Owner Salutation3</label>
							
							<input type="text"  id="ownerSalutation3" placeholder="ownerSalutation3" name="ownerSalutation3" value="${merchant.ownerSalutation3}" disabled="disabled">
						</div>
 					<div class="input-field col s12 m4 l4">
							<label for="email">Owner Name3</label> 
							<input type="text"  id="ownerName3" placeholder="ownerName3" name="ownerName3" value="${merchant.ownerName3}" disabled="disabled">
						</div>

					 <div class="input-field col s12 m4 l4">
							<label for="ownerContactNo3">Owner ContactNo3</label>
						
							<input type="text"  id="ownerContactNo3" placeholder="ownerContactNo3" name="ownerContactNo3" value="	${merchant.ownerContactNo3}" disabled="disabled">
						</div>

					

				</div>

			</div>
			<div style="display: none;" id="owner33">
				<!-- <div id="owner33"> -->
				<div class="row">
					 <div class="input-field col s12 m4 l4">
							<label for="passportNo3">NRIC Passport3</label>
							
							<input type="text"  id="passportNo3" placeholder="passportNo3" name="passportNo3" value="${merchant.passportNo3}" disabled="disabled">
						</div>
 						<div class="input-field col s12 m4 l4">
							<label for="residentialAddress3">Residential Address3</label>
							
							<input type="text" id="residentialAddress3" placeholder="residentialAddress3" name="residentialAddress3" value="${merchant.residentialAddress3}" disabled="disabled">
						</div>

					
				</div>
			</div>
			<!-- owner Details4 -->
			<div style="display: none;" id="owner4">
				<div class="row">
					 <div class="input-field col s12 m4 l4">
							<label for="ownerSalutation4">Owner Salutation4</label>
							
							<input type="text"  id="ownerSalutation4" placeholder="ownerSalutation4" name="ownerSalutation4" value="${merchant.ownerSalutation4}" disabled="disabled">
						</div>

					 <div class="input-field col s12 m4 l4">
							<label for="ownerName4">Owner Name4</label>
							
							<input type="text"  id="txtRegName" placeholder="ownerName4" name="ownerName4" value="${merchant.ownerName4}" disabled="disabled">
						</div>

					 <div class="input-field col s12 m4 l4">
							<label for="ownerContactNo4">Owner ContactNo4</label>
							
							<input type="text" id="ownerContactNo4" placeholder="ownerContactNo4" name="ownerContactNo4" value="${merchant.ownerContactNo4}" disabled="disabled">
						</div>

					

				</div>
			</div>


			<div style="display: none;" id="owner44">
				<div class="row">
					 <div class="input-field col s12 m4 l4">
							<label for="email">NRIC Passport4</label>
							<input type="text" id="passportNo4" placeholder="passportNo4" name="passportNo4" value=" ${merchant.passportNo4}" disabled="disabled">
						</div>

					 <div class="input-field col s12 m4 l4">
							<label for="email">Residential Address2</label>
							
							<input type="text"  id="residentialAddress2" placeholder="residentialAddress2" name="residentialAddress2" value="${merchant.residentialAddress2}" disabled="disabled">
						</div>

					
				</div>
			</div>
			<!--  owner Details5 -->

			<div style="display: none;" id="owner5">
				<div class="row">
					 <div class="input-field col s12 m4 l4">
							<label for="email">Owner Salutation5</label>
							
							<input type="text" id="ownerSalutation5" placeholder="ownerSalutation5" name="ownerSalutation5" value="${merchant.ownerSalutation5}" disabled="disabled">
						</div>

					 <div class="input-field col s12 m4 l4">
							<label for="ownerName5">Owner Name5</label>
							
							<input type="text" id="ownerName5" placeholder="ownerName5" name="ownerName5" value="${merchant.ownerName5}" disabled="disabled">
						</div>

					 <div class="input-field col s12 m4 l4">
							<label for="ownerContactNo5">Owner ContactNo5</label>
							
							<input type="text" id="ownerContactNo5" placeholder="ownerContactNo5" name="ownerContactNo5" value="${merchant.ownerContactNo5}" disabled="disabled">
						</div>

					

				</div>
			</div>
			<div style="display: none;" id="owner55">

				<div class="row">
					 <div class="input-field col s12 m4 l4">
							<label for="passportNo5">NRIC Passport5</label>
							
							<input type="text"  id="passportNo5" placeholder="passportNo5" name="passportNo5" value="${merchant.passportNo5}" disabled="disabled">
						</div>

					 <div class="input-field col s12 m4 l4">
							<label for="residentialAddress5">Residential Address5</label>
							
							<input type="text"  id="residentialAddress5" placeholder="residentialAddress5" name="residentialAddress5" value="${merchant.residentialAddress5}" disabled="disabled">
						</div>

					
				</div>

			</div>
						  </div>
					  </div>
					 </div>
					 
				
				<div class="row">
				<div class="col s12">
				   <div class="card border-radius"> 
				    <div class="d-flex align-items-center">
					   <h5>Office Details </h5>
					  </div> 
						 <div class="input-field col s12 m6 l6">
							
							 <label for="Email">Office Email</label>
							 <input type="text" id="officeEmail" placeholder="officeEmail" name="officeEmail" value="${merchant.officeEmail}" disabled="disabled">
						</div> 
						
						 <div class="input-field col s12 m6 l6">
							 
							 <label for="Website">Website</label>
							 <input type="text" id="website" placeholder="website" name="website" value="${merchant.website}" disabled="disabled">
						</div> 
						
						<div class="input-field col s12 m6 l6">
							
							 <label for="Office_No">Office No</label>
							 <input type="text"  id="officeNo" placeholder="officeNo" name="officeNo" value="${merchant.officeNo}" disabled="disabled">
						</div> 
						
						<div class="input-field col s12 m6 l6">
							 
							 <label for="Fax No">Fax No</label>
							 <input type="text" id="faxNo" placeholder="faxNo" name="faxNo" value="${merchant.faxNo}" disabled="disabled">
						</div> 
				  </div> 
				</div>
				</div>
				
				<div class="row">
				<div class="col s12">
				   <div class="card border-radius"> 
				    <div class="d-flex align-items-center">
					   <h5>  Business Details & Documents</h5>
					  </div> 
						 <div class="input-field blue-input col s12 m4 l4">
							  
						  <label for="Business">Business Type</label>
							<input type="text"  id="businessType" placeholder="businessType" name="businessType" value="${merchant.businessType}" disabled="disabled">
						</div>
						
						 <div class="input-field blue-input col s12 m4 l4">
						 			  
                	
						  <label for="Company">Company Type</label>
							<input type="text" id="companyType" placeholder="companyType" name="companyType" value="${merchant.companyType}" disabled="disabled">
						</div>
						
						 <div class="input-field blue-input col s12 m4 l4">
						    
						  <label for="Nature">Nature of Business</label>
						  <input type="text" id="natureOfBusiness" placeholder="natureOfBusiness" name="natureOfBusiness" value="${merchant.natureOfBusiness}" disabled="disabled">
						
						</div> 
				  </div> 
				</div>
				</div>
				
				<div class="row">
				<div class="col s12">
				   <div class="card border-radius"> 
				    <div class="d-flex align-items-center">
					   <h5>Bank Details  & Other Details</h5>
					  </div> 
						 <div class="input-field col s12 m4 l4">
							 <!-- <input id="Email" type="text" class="validate"> -->
							 <label for="Bank Name">Bank Name</label>
							 <input type="text" id="bankName" placeholder="bankName" name="bankName" value="${merchant.bankName}" disabled="disabled">
						</div> 
						
						 <div class="input-field col s12 m4 l4">
							 <!-- <input id="Account No" type="text" class="validate"> -->
							 <label for="Account No">Account No</label>
							 <input type="text" id="bankAccNo" placeholder="bankAccNo" name="bankAccNo" value="${merchant.bankAccNo}" disabled="disabled">
						</div> 
						
						<div class="input-field col s12 m4 l4">
							 <input id="Account No" type="text" class="validate">
							 <label for="Account No">Account No</label>
						</div>  
						
						<div class="input-field col s12 m4 l4">
							<!--  <input id="Refferral ID" type="text" class="validate"> -->
							 <label for="Refferral ID">Referral ID</label>
							<input type="text" id="referralId" placeholder="referralId" name="referralId" value="${merchant.referralId}" disabled="disabled">
						</div> 
						
						<div class="input-field col s12 m4 l4">
							 <!-- <input id="No of Readers" type="text" class="validate"> -->
							 <label for="Trading Name">Trading Name</label>
							 <input type="text" id="tradingName" placeholder="tradingName" name="tradingName" value="${merchant.tradingName}" disabled="disabled">
						</div> 
						
						<div class="input-field col s12 m4 l4">
							<!--  <input id="No of Readers" type="text" class="validate"> -->
							 <label for="No of Readers">No of Readers</label>
							 <input type="text" id="noOfReaders" placeholder="noOfReaders" name="noOfReaders" value="${merchant.noOfReaders}" disabled="disabled">
							 
						</div> 
						
						
						<div class="input-field col s12 m4 l4">

							 <label for="Refferral ID">Signed package</label>
							 <input type="text" id="signedPackage" placeholder="signedPackage" name="signedPackage" value="${merchant.signedPackage}" disabled="disabled">
					
						</div> 
						
						
						<div class="input-field col s12 m4 l4">
							 <!-- <input id="Waiver Month" type="text" class="validate"> -->
							 <label for="Waiver Month">Waiver Month</label>
							 <input type="text" id="wavierMonth" placeholder="wavierMonth" name="wavierMonth" value="${merchant.wavierMonth}" disabled="disabled">
							
						</div> 
						
						<div class="input-field col s12 m4 l4">
							<!--  <input id="Remarks" type="text" class="validate"> -->
							 <label for="Remarks">Remarks</label>
							 <input type="text" id="statusRemarks" placeholder="statusRemarks" name="statusRemarks" value=" ${merchant.statusRemarks}" disabled="disabled">
		
						</div> 
						
						
						<div class="input-field col s12 m4 l4">

							 <label for="Status">Status</label>
							 <input type="text" id="status" placeholder="status" name="status" value="${merchant.status}" disabled="disabled">
							
						</div> 
						
						<div class="input-field col s12 m4 l4">
							
							 <label for="Year Incorporated">Year Incorporated</label>
							 <input type="text" id="yearIncorporated" placeholder="yearIncorporated" name="yearIncorporated" value="${merchant.yearIncorporated}" disabled="disabled">
							
						</div> 
						
						
						<div class="input-field col s12 m4 l4">

							 <label for="Agent Name">Agent Name</label>
							 <input type="text"  id="agentName" placeholder="agentName" name="agentName" value=" ${merchant.agentName}" disabled="disabled">
				
						</div> 
						<div class="input-field col s12 m4 l4">

							 <label for="Account Type">Account Type</label>
							 <input type="text"  id="accType" placeholder="accType" name="accType" value=" ${merchant.accType}" disabled="disabled">
				
						</div>
				  </div> 
				</div>
				</div>
				
				<div class="row">
						<div class="input-field col s12 m4 l4">
												<label for="businessRegNo">Documents</label>
									<input type=hidden name="fileId" value="${merchant.fileId}">
									<%-- ${merchant.documents} --%>
									<input type="text" id="documents" placeholder="documents" name="documents" value="${merchant.documents}" disabled="disabled">
									<div><c:if test="${merchant.formFName != null}">
	     
					        <input type="text" id="formFName" placeholder="formFName" name="formFName" value="${merchant.formFName}" disabled="disabled">
				         </c:if></div>
				<div><c:if test="${merchant.docFName != null}">
					<br><%-- ${merchant.docFName} --%>
					<input type="text" id="docFName" placeholder="docFName" name="docFName" value="${merchant.docFName}" disabled="disabled">
				</c:if></div>
				<div><c:if test="${merchant.payFName != null}">
					<br><%-- ${merchant.payFName} --%>
					<input type="text" id="payFName" placeholder="payFName" name="payFName" value="${merchant.payFName}" disabled="disabled">
				</c:if></div>
			</div>
		</div>
				
				<div class="row">
						<div class="input-field col s12 m4 l4">
						<label for="preAuth">Pre-Auth</label> 
                              <input type="text" id="preAuth" placeholder="preAuth" 
                              name="preAuth" value="${merchant.preAuth}" disabled="disabled">
                          </div>
                          
                          <div class="input-field col s12 m4 l4">
	                          <label for="autoSettled">Auto Settled</label>
							
							
							<input type="text"
								id="autoSettled" placeholder="autoSettled" name="autoSettled"
								value="${merchant.autoSettled}" disabled="disabled">
                          </div>
                          
                          <div class="input-field col s12 m4 l4">
                          <label for="auth3DS">3DS</label>
						
						<input type="text"
							id="auth3DS" placeholder="auth3DS" name="auth3DS"
							value="${merchant.auth3DS}" disabled="disabled">
                          </div>
                   </div>
                   </div></div></div></div>
                 
                    <div class="row">
				<div class="col s12 m4 l4"></div>
				 <div class="col s12 m4 l4">
				  <div class="button-class" style="float:left;">	
					<button type="button"  id="testing" onclick=" return load()" class="btn btn-primary blue-btn" >Confirm</button>
					<button type="button" onclick="load1()" class="export-btn waves-effect waves-light btn btn-round indigo">Cancel</button>
					</div>
				<div class="col s12 m4 l4"></div>
				</div>  
                   
                   </div>
								
	<style>
						
				.select-wrapper .caret { fill: #005baa;}				
				 .blue-btn { background-color:#005baa; color:#fff;border-radius:20px;}
				.button-class { float:right;}
				.float-right {float:right; }
				</style>	
    							
</div>								
			 
	</form:form>
	
	

</body>
</html>