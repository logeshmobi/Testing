<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@page import="com.mobiversa.payment.controller.PromotionAdminController"%>
<%@page import="com.mobiversa.common.bo.Merchant"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script type="text/javascript" src="https://ajax.googleapis.com/ajax/libs/jquery/3.1.0/jquery.min.js"></script>
   <script type="text/javascript"  src="${pageContext.request.contextPath}/resourcesNew/js/urlHide.js"></script>
 
 <script type="text/javascript" src="${pageContext.request.contextPath}/resourcesNew/js/plugins/select2.min.js"></script>
<html lang="en-US">
<head>

 <meta content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no" name="viewport">

<style>

.error {
	color: red;
	font-weight: bold;
}

.form-control {
	width: 100%;
	
	}


img {
    transition: -webkit-transform 0.25s ease;
    transition: transform 0.25s ease;
     cursor: zoom-in;
     cursor:-webkit-zoom-in;
    cursor:-moz-zoom-in;
     cursor: zoom-out;
     cursor:-webkit-zoom-out;
    cursor:-moz-zoom-out;
    overflow:hidden;
   
   
}
img:active {
    -webkit-transform: scale(2);
    transform: scale(2);
}

</style>


<script type="text/javascript">
jQuery(document).ready(function() {
$('#status').select2();



});  
    </script>

<script lang="JavaScript">


function loadSelectData() {
	//alert("test")	;
		
		var e = document.getElementById("status").value;
	
	   var e1 = document.getElementById("emailPreview1").value;    
	   document.getElementById("emailPreview").value = e1;
	  // alert("check test email:"+ document.getElementById("emailPreview").value);
	    /*  if (e == null || e == "PENDING") { */
	    	 
	    	 if (e == null || e == "") {
	    
				alert("Please change the Status");
				 //form.submit = false; 
				return false;
				} 
	    
	    	
	 		
	        if((e1 == null || e1 == '')&& (e=="APPROVED")){
	        	
	        
	        		  alert("Enter EmailPreview");
					 // alert("test status:" + e);
					  
					  
					  //document.location.href = '${pageContext.request.contextPath}/promotionAdmin/editMerchantPromotion';
					  //form.submit;
					  return false;
				  }   
	        
	        
	        else if(e=="APPROVED")
	        {
	        	
	        
	        if(!validateEmail(document.form1.emailPreview,10,20))
	 		{
	 		
	 		return false;
	 		}
	        }
	    
	}
	    
	 function enableTextbox1() {
			//alert("test status:" );
			
			
			if (document.getElementById("status").value == "APPROVED" ) {
	           document.getElementById("emailDisplay").style.display = 'none';
				
				
				//alert("check status:"  + document.getElementById("status").value );
				//document.getElementById("button1").style.display = '';
				document.getElementById("button").value= 'Submit';	
				
			}
				
			
			}   
	 
	 
	 
	 function validateEmail(inputtxt) {
			//alert(inputtxt);
			var field = inputtxt.value;
			//var uemail = document.registration.email;  
			var mailformat = /^\w+([\.-]?\w+)*@\w+([\.-]?\w+)*(\.\w{2,5})+$/;
			if (field.match(mailformat)) {
				//document.registration.desc.focus();  
				return true;
				
			} else if (field.length==0)
			 {
				alert("You have entered  "+inputtxt.name+" address!");
				//uemail.focus();  
				return false;
			} else {
			alert("You have entered an invalid "+inputtxt.name+" address!");
			}
		}
	 function disableRow1() {

			//alert("test123");

			var i = document.getElementById("status").value;
			//var e = ${mobiPromo.status};
			//alert(i);

			if (i == "APPROVED") {
				//document.getElementById("agType").value;

				document.getElementById("emailDisplay").style.display = '';
				
				
				//alert(i);
				//document.getElementById("button1").style.display = '';
				document.getElementById("button").value= 'MailPreview';

				

			} else if (i == "PENDING" || i== "CANCELLED" || i=="DELETED" || i=="REPOST") {

				document.getElementById("emailDisplay").style.display = 'none';

				document.getElementById("button").value="Submit";
			
			}
			
			

		}


	 function validateEmail(inputtxt) {
			//alert(inputtxt);
			var field = inputtxt.value;
			//var uemail = document.registration.email;  
			var mailformat = /^\w+([\.-]?\w+)*@\w+([\.-]?\w+)*(\.\w{2,5})+$/;
			if (field.match(mailformat)) {
				//document.registration.desc.focus();  
				return true;
				
			} else if (field.length==0)
			 {
				alert("You have entered  "+inputtxt.name+" address!");
				//uemail.focus();  
				return false;
			} else {
			alert("You have entered an invalid "+inputtxt.name+" address!");
			}
		}
	 
	 
function loadCancelData() 
{
	//alert("fcancel data");
	
	
	 document.location.href = "${pageContext.request.contextPath}/promotionAdmin/list/1";
	form.submit;
}


function loadtype(type,type1){
	//alert(' type'+type);
	// var bustype = document.getElementById("businessType1").value;
	//alert('type1 : '+type1);
	//var bustype1 = document.getElementById("businessType");
	//alert('ddd :'+bustype1); 
	var i=0;
	for(i = 0 ; i<type1.options.length ; i++){
		if (type1.options[i].value == type)
			{

			// Item is found. Set its property and exit

			type1.options[i].selected = true;
		}/* else{
			type1.options[0].selected = true;
		} */
	}  

}

function loadDropData()
{
//status
	//alert('status');
	 var status = document.getElementById("status1").value;
	//alert('status val : '+status);
	var status1 = document.getElementById("status"); 
	loadtype(status,status1);
	}

</script>

</head>
<body onload="loadDropData();disableRow1();enableTextbox1()" class="sidebar-mini fixed">

<form action="${pageContext.request.contextPath}<%=PromotionAdminController.URL_BASE%>/editMerchantPromotion"
		method="post" name="form1" id="form1"  commandName="mobiPromo" >
		
		
			<input type="hidden" name="${_csrf.parameterName}"
			value="${_csrf.token}" /> <input type="hidden" name="id"
			value="${mobiPromo.id}" />
		<!-- <div style="overflow:auto;border:1px;width:90%"> -->
				
			<div class="content-wrapper">
        
        
        <div class="row">
			
            <div class="col-md-12 formContianer">
              <h3 class="card-title">Update Merchant EZYAds Details</h3>
            <!--   <a class="btn btn-primary btn-flat pull-right" href="#">Add New <i class="fa fa-lg fa-plus"></i></a> -->
             <!--  <div class="col-md-12 formContianer tableEdit"> -->
           <div class="card" >
            	
           <!--    <h3 class="card-title userTitle">Edit<i class="fa fa-pencil pull-left" aria-hidden="true"></i> <span class="pull-right"> -->
                                   <h3 class="card-title userTitle">Merchant Name:${mobiPromo.merchantName}			
						<input type="hidden" class="form-control" id="merchantName" name="merchantName" value="${mobiPromo.merchantName}">
					Mid:${mobiPromo.mid}
												
							<input type="hidden" class="form-control" id="mid" name="mid" value="${mobiPromo.mid}" >
						<!-- </span> --></h3>
						
						
						<div class="row">
				<div class="form-group col-md-4">
								<div class="form-group">
									<label  for="lName">EZYAds Name</label>
								<%-- <input type="text" class="form-control" style="width:100%" id="promoName" name="promoName" value="${mobiPromo.promoName}" readonly="readonly"> --%>
								<textarea row="5"  column="2" max-length="120" class="form-control" id="promoName" style="width:100%;word-break:break-all;" name="promoName"  readonly="readonly">${mobiPromo.promoName}</textarea>
								</div>
							</div>
							<div class="form-group col-md-4">
								<div class="form-group">
									<label  for="fName">EZYAds Code</label>
								<input type="text" class="form-control" style="width:100%" id="pCode" name="pCode" value="${mobiPromo.pCode}" readonly="readonly">
								</div>
					
					</div>
					
					<div class="form-group col-md-4">
								<div class="form-group">
									<label  for="fName">EZYAds Desc</label>
								<textarea  readonly="readonly" row="2"  column="2" max-length="120" class="form-control" id="promoDesc" style="width:100%;word-break:break-all;" name="promoDesc"  readonly="readonly">${mobiPromo.promoDesc}</textarea>
								
								</div>
					
					</div></div>
						
				<div class="row">
					
					
					<div class="form-group col-md-4">
								<div class="form-group">
									<label  for="fName">EZYAds Image</label>
								</div>	
									
								<input type="hidden" class="form-control" id="promoLogo1" placeholder="promoLogo1" name="promoLogo1" value="${mobiPromo.promoLogo1}" />
			<img   width="100" height="100" src="data:image/jpg;base64,<c:out value='${mobiPromo.promoLogo1}'/>" />
			
				<c:choose>
							<c:when test="${mobiPromo.promoLogo2 != null}">
		
		<input type="hidden" class="form-control" id="promoLogo2" placeholder="promoLogo2" name="promoLogo2" value="${mobiPromo.promoLogo2}" />
			
			<img  width ="100" height="100" src="data:image/jpg;base64,<c:out value='${mobiPromo.promoLogo2}'/>" />
			
			
			
</c:when>
</c:choose>
							
					</div><!-- </div>
					<div class="row"> -->
			
		
			<div class="form-group col-md-4">
								<div class="form-group">
									<label  for="fName">Mobile User Name</label>
								<input type="text" class="form-control" id="deviceId" style="width:100%" name="username" value="${mobiPromo.username}" readonly="readonly">
								</div>
					
					</div><!-- </div>
					<div class="row"> -->
					
					
					
					<div class="form-group col-md-4">
								<div class="form-group">
									<label  for="fName">MessageCount</label>
								<input type="text" class="form-control" id="points" style="width:100%" name="points" value="${mobiPromo.points}" readonly="readonly">
								</div>
					
					</div><!-- </div>
					
					<div class="row"> -->
					<div class="form-group col-md-4">
								<div class="form-group">
									<label  for="fName">ValidFrom</label>
								<input type="text" class="form-control" id="validFrom" style="width:100%" name="validFrom" value="${mobiPromo.validFrom}" readonly="readonly">
								</div>
					
					</div>
					
					<div class="form-group col-md-4">
								<div class="form-group">
									<label  for="fName">ValidTo</label>
								<input type="text" class="form-control" id="validTo" style="width:100%" name="validTo" value="${mobiPromo.validTo}" readonly="readonly">
								</div>
					
					</div>
	
	
		<div class="form-group col-md-4">
								<div class="form-group">
									<label  for="fName">Status</label>
							
				<input type="hidden" id="status1" value="${mobiPromo.status}" />
							<select  class="form-control" id="status"  name="status"  path="status" style="width:100%" onchange="disableRow1();">
							
									<option value="">Status</option>
								<!-- 	<option value="PENDING">Pending</option> -->
									<option value="CANCELLED">Cancelled</option>
									<!-- <option value="DELETED">Deleted</option> -->
									<!-- <option value="REPOSTED">Reposted</option> -->
									<option value="APPROVED">Approved</option>
								<!-- 	<option value="ACTIVE">Active</option> -->
									
									
									</select>
		
						
						
						
		
					</div>
					</div>
					
					<div style="display: none;" id="emailDisplay">
			<div class="form-group col-md-4">
								<div class="form-group">
									<label  for="fName">EmailPreview</label>
			<input type="text" class="form-control" id="emailPreview1" placeholder="emailPreview" name="emailPreview1" />
			<input type="hidden" id="emailPreview"  name="emailPreview" value="${mobiPromo.emailPreview}" />
		</div>
		</div>
		</div>					
					</div>
					</div>
              </div>
				 	
					<input class="btn btn-primary icon-btn" type="submit" id="button"
				onclick=" return loadSelectData()" value="Submit">
				
      <button type="button"  class="btn btn-default icon-btn" onclick="return loadCancelData()">Cancel</button>
     
					
				  </div>   </div>	 <!--  </div> -->
              </form>
					
	
		
		
		
		</body>







