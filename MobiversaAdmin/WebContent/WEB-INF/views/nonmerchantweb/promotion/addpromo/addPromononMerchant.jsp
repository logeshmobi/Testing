<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@page import="com.mobiversa.payment.controller.NonMerchantWebAddPromoController"%>
<%@page import="com.mobiversa.common.bo.AgentUserRole"%>


<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>
	<!-- <script type="text/javascript" src="https://ajax.googleapis.com/ajax/libs/jquery/3.1.0/jquery.min.js"></script> -->
  
 <script type="text/javascript" src="${pageContext.request.contextPath}/resourcesNew/js/plugins/select2.min.js"></script>

 <script type="text/javascript"  src="${pageContext.request.contextPath}/resourcesNew/js/jquery-2.1.4.min.js"></script>
<meta charset="UTF-8">
<meta
	content="width=device-width,user-scalable=yes">
	
	<!-- <meta
	content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=yes"
	name="viewport"> -->

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script type="text/javascript">
window.history.replaceState({}, document.title, "/MobiversaAdmin/" + "promotionwebNonmerchant1/");
</script>
<script type="text/javascript">
$().ready(function() {
  //$('[type="file"]').change(function() {
  $("#imageFile").change(function() {
    var fileInput = $(this);
    if (fileInput.length && fileInput[0].files && fileInput[0].files.length) {
      var url = window.URL || window.webkitURL;
      var image = new Image();
      image.onload = function() {
        //alert('Valid Image');
       
      };
      image.onerror = function() {
    alert('Please select a valid image file');
        document.getElementById("imageFile").value = '';
        return false;
     
      };
      image.src = url.createObjectURL(fileInput[0].files[0]);
    }
  });
});

</script>

<script type="text/javascript">
$().ready(function() {
  //$('[type="file"]').change(function() {
  $("#imageFile1").change(function() {
    var fileInput = $(this);
    if (fileInput.length && fileInput[0].files && fileInput[0].files.length) {
      var url = window.URL || window.webkitURL;
      var image = new Image();
      image.onload = function() {
        //alert('Valid Image');
       
      };
      image.onerror = function() {
    alert('Please select a valid image file');
        document.getElementById("imageFile1").value = '';
        return false;
     
      };
      image.src = url.createObjectURL(fileInput[0].files[0]);
    }
  });
});

</script>
<style>
.error {
	color: red;
	font-weight: bold;
}	
<style>  
  .tooltip {
    position: relative;
    display: inline-block;
    border-bottom: 1px dotted black;
}


err: {
    container: 'tooltip'
}


.tooltip .tooltiptext {
    visibility: hidden;
    width: 120px;
    background-color: #555;
    color: #fff;
    text-align: center;
    border-radius: 6px;
    padding: 5px 0;
    position: absolute;
    z-index: 1;
    bottom: 125%;
    left: 50%;
    margin-left: -60px;
    opacity: 0;
    transition: opacity 1s;
}

.tooltip .tooltiptext::after {
    content: "";
    position: absolute;
    top: 100%;
    left: 50%;
    margin-left: -5px;
    border-width: 5px;
    border-style: solid;
    border-color: #555 transparent transparent transparent;
}

.tooltip:hover .tooltiptext {
    visibility: visible;
    opacity: 1;
}
.error {
	color: red;
	font-weight: bold;
}

.asterisk_input:before {
	content: " *";
	color: #062630;
	position: absolute;
	margin: 0px 0px 0px -20px;
	font-size: xx-large;
	padding: 0 5px 0 0;
}



.rangeInput{
color:red;
}
</style>
</head>


<script lang="JavaScript">
 

jQuery(document).ready(function() {
	
	
$('#sendType').select2();
$('#deviceId').select2();
});
</script>

<script lang="JavaScript">



function updateTextInput(val) {
	
    document.getElementById('points').value=val; 
    
    $("#points").click(function () {
    	//alert("points status" + points);
        $("#slider").slider("option", "value", $("#points").val());

    });
  }
 function msgCount()
{
	//alert("check message count" + points);
	
	var points1 = document.getElementById("points").value;
	var points2= "${merchantPromo.points}";
	
	
	if(points1 >= points2)
	{
		alert("NonMerchant Count Dont exist:" + points1);
		return false;
	}
	
	else
		{
		//alert("merchant Count:" + e);
		return false;
		
		}
	} 
	
	
	
function enableTextbox() {
	//alert("test points:");
	if (document.getElementById("points").value == "0") {
		
	document.getElementById("button").disabled = true;
	alert("you are not eligible for this promotion because NonMerchant Count 0");
	
	//alert("test points:" + points);
	}
	else {
	document.getElementById("button").disabled = false;
	//alert("test points111:" + points);
	}
	} 
	
	
function promDesc()
{
	
	 
	 if(document.getElementById("Description").value>= "120")
		 {
		 alert("EZYAds desc maximum 120 characters.!");
		 }
}
	

	function loadSelectData() {
	
		/* var e = document.getElementById("points").value;
		var a = "${merchantPromo.points}"; */
	

	 if(!allLetterSpaceSpecialCharacter(document.form1.promoName,3,120))
		{
		
		return false;
		

		
		}
		
			 var a2 = document.getElementById("sendType").value;
	       
	       if (a2 == null || a2 == '' ) {
	       
				alert("Please select SendType");
				// form.submit = false; 
				return false;
				}
	       
	       
	       if(!allLetterSpaceSpecialCharacter(document.form1.promoDesc,3,120))
			{
			
			return false;
			}
		 
		 var a1 = document.getElementById("imageFile").value;
	       
	       if (a1 == null || a1 == '' ) {
	       
				alert("Please upload Image File");
				// form.submit = false; 
				return false;
				}
	       
      
		
	        
            var a4 = document.getElementById("Tid").value;
	       
	       if (a4 == null || a4 == '' ) {
	       
				alert("Please select TID");
				// form.submit = false; 
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
			alert("Please input the " + inputtxt.name + " between " + mnlen
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
	
		
		
		function allLetterSpaceSpecialCharacter(inputtxt, minlength, maxlength) {
			//alert("TEste :"+ inputtxt);
			var field = inputtxt.value;
			var mnlen = minlength;
			var mxlen = maxlength;
			// var uname = document.registration.username;  
			var letters = /^[ A-Za-z_()./&-@% !]*$/; /* /^[a-zA-Z0-9- ]*$/; *//*  /^[A-Za-z]+$/; */
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
	
		
		
		
		function allnumeric(inputtxt, minlength, maxlength) {
			var field = inputtxt.value;

			var mnlen = minlength;
			var mxlen = maxlength;
			//var uzip = document.registration.zip;  
			var numbers = /^[0-9]+$/;
			if ((field.length < mnlen) || (field.length > mxlen)) {
				alert("Please input the " + inputtxt.name + " between " + mnlen
						+ " and " + mxlen + " characters");
				return false;
			} else if (field.match(numbers)) {
				// Focus goes to next field i.e. email.  
				//document.registration.email.focus();  
				return true;
			} else {
				alert(inputtxt.name + ' must have numeric characters only');
				//uzip.focus();  
				return false;
			}
		}

</script>



<script type="text/javascript">
	function imageSize (){
		$('#imageFile').change(function(){
			alert("test data")
			var imageFile=this.files[0]
			alert(imageFile.size||imageFile.fileSize)
		})
	}
	</script>

<body onload="enableTextbox();">

					
					
					<!-- action="agentDetailsReviewAndConfirm" -->
					<!-- business details block start here -->
				
						<form:form method="post" action="addMerchantPromotion?${_csrf.parameterName}=${_csrf.token}"	 commandName="merchantPromo"
					name="form1" id="form" enctype="multipart/form-data" >  <!--  onsubmit ="return merchantPoints();" -->
				
					<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}">
					<!-- <div style="overflow:auto;border:1px;width:100%"> -->
					 <div class="content-wrapper">
        
        
        <div class="row">
			
            <div class="col-md-12 formContianer">
              <h3 class="card-title">Create EZYAds
              <!-- <a class="btn btn-primary btn-flat pull-right" href="#">Create EZYAds <i class="fa fa-lg fa-plus"></i></a> --></h3>
              
            <!--   <div class="col-md-12 formContianer tableEdit"> style="width: 90rem;"-->
           <div class="card" >
            	
            <!--   <h3 class="card-title userTitle">Add<i class="fa fa-pencil pull-left" aria-hidden="true"></i> <span class="pull-right"> -->
                                 <h3 class="card-title userTitle"> NonMerchant Name: ${merchant.businessName}				
						<input type="hidden" class="form-control" id="businessName" name="businessName" value="${merchant.businessName}	" >
					Mid:	${merchant.mid.mid} 
												
						<input type="hidden" class="form-control" id="mid" name="mid" value="${merchant.mid.mid}">
						<!-- </span> --> </h3>
						<!-- <div class="card-body formbox"> -->
                     <div class="row">									
                            <div class="form-group col-md-4 i">
								<div class="form-group">
									<label for="ContactNo">EZYAds Name</label>

									<%-- <form:input type="text" class="form-control" id="promoName" placeholder="promoName" name="promoName" path="promoName" /> --%>
									<form:textarea  rows="2" cols="2" maxlength="120" class="form-control" id="promoName" placeholder="promoName" name="promoName" path="promoName"></form:textarea>
								</div>
							</div>
								                                                        
							<div class="form-group col-md-4">
								<div class="form-group">
									<label>Send To?</label>
									
									<form:select class="form-control" name="sendType" path="sendType" id="sendType">
										<option value="">Send To?</option>
										<option value="Mycustomer">MyCustomer</option>
                                        <!-- <option value="All">ALL</option>
                                        <option value="Rejected">Rejected</option>
                                        <option value="Active">Active</option> -->
									</form:select>									
								</div>
							</div>
						
								                                                        
							<div class="form-group col-md-4">
								<div class="form-group">
									<label for="ContactNo">EZYAds Desc</label>
	   							<%-- <form:input type="text"  rows="3" cols="" maxlength="50" class="form-control" id="Description" placeholder="Description" name="promoDesc" path="promoDesc" onclick= "promDesc()"/> --%>
	   							<form:textarea  rows="2" cols="2" maxlength="120" class="form-control" id="Description" placeholder="Description" name="promoDesc" path="promoDesc" onclick= "promDesc()"></form:textarea>
								</div>
							</div>
						</div><!-- </div> -->
	
	
	            

	<div class="row">
				<div class="form-group col-md-4">
					<div class="form-group">
						<label for="mailFile">EZYAds Image</label>
				
					
					
						<form:input type="file" class="form-control" id="imageFile"
							placeholder="imageFile" name="imageFile" path="imageFile"   onclick="fileUpload()"/> 
						 <span class="tooltiptext" style="color: #ff4000;" >Upload maximum of size 3MB Images</span>	
							<!-- onclick="Upload()" onclick="Upload1()" -->
							
							<form:input type="file" class="form-control" id="imageFile1"
							placeholder="imageFile1" name="imageFile1" path="imageFile1"  />
						<span class="tooltiptext" style="color: #ff4000;" >Upload maximum of size 3MB Images</span>
							<div><c:if test="${responseData  != null}">
			<H4 style="color:blue;" align="center">${responseData}</H4>
			
		</c:if></div>
						
					</div>
					
					</div>
						                                                        
							<div class="form-group col-md-4">
							
								<div class="form-group">
								
									<label for="ContactNo">Messsage Count</label>
									
									<div class="form-group">
									<input type="range" name="rangeInput"  id="rangeInput" min="1" max="${merchantPromo.points}"  value="${merchantPromo.points}" onchange="updateTextInput(this.value);">
									</div>
									<form:input type="text" class="form-control" id="points" placeholder="points" name="points"  path="points"  value= "${merchantPromo.points}" onchange=" return msgCount();updateTextInput(this.value)" />
									
									
									
								</div>
							</div>
					
					<div class="form-group col-md-4">
						<div class="form-group">

							<label for="deviceId">Mobile User</label>
						
							<%-- <form:select class="form-control" name="Tid"
								id="Tid" path="Tid">
								<option selected value=""><c:out value="Tid" /></option>
								<c:forEach items="${tidList}" var="Tid">
									<option <c:out value="${tid}"/>>${Tid}</option>


								</c:forEach>
							</form:select> --%>
						
		
		
							<form:select class="form-control" name="username"
								id="username" path="username" style="width:100%">
								<option selected value=""><c:out value="mobile Username" /></option>
								<c:forEach items="${mobUsername}" var="mobUsername">
									<option value="${mobUsername}">${mobUsername}</option>


								</c:forEach>
							</form:select>
					</div>	</div>	</div>
						
			<div class="row">

				<div class="form-group col-md-4">
					<div class="form-group">
						<label for="From_Date">Valid_From</label><input type="hidden"
							name="validityDate1" id="validityDate1" value="${validFrom}"> <input
							type="text" class="form-control" id="datepicker" name="validFrom" path="validFrom"
							style="width: 100%" placeholder="dd/mm/yyyy">

					</div>
				</div>
				
				
				<div class="form-group col-md-4">
					<div class="form-group">
						<label for="From_Date">Valid_To</label><input type="hidden"
							name="validityDate2" id="validityDate2" value="${validTo}"> <input
							type="text" class="form-control" id="datepicker1" name="validTo" path="validTo"
							style="width: 100%" placeholder="dd/mm/yyyy">

					</div>
				</div>
							
							
				
				</div>
				</div>
					
			
				
				
				
				</div>	
			
			
					
			
							
							
					</div>		
					
				
				
				<button class="btn btn-primary icon-btn" type="submit"  id ="button" onclick="return loadSelectData();msgCount();showFileSize()" >Submit</button>
				</div>
				
				
				</form:form>	
				</body>
				</html>
					
						
					
</body>
</html>
