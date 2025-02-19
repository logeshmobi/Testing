<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@page import="com.mobiversa.payment.controller.NonMerchantWebAddPromoController"%>
<%@page import="com.mobiversa.common.bo.Merchant"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>


<script type="text/javascript" src="https://ajax.googleapis.com/ajax/libs/jquery/3.1.0/jquery.min.js"></script>
  <script type="text/javascript"  src="${pageContext.request.contextPath}/resourcesNew/js/urlHide.js"></script>
 <script type="text/javascript" src="${pageContext.request.contextPath}/resourcesNew/js/plugins/select2.min.js"></script>
 <script type="text/javascript"  src="${pageContext.request.contextPath}/resourcesNew/js/jquery-2.1.4.min.js"></script>
<html lang="en-US">
<head>
<meta charset="UTF-8">
  <meta content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no" name="viewport">
 
 
 
</head>
<style>



 /* .frame { height: 100%; width: 100%; overflow: hidden; } 
.zoomin img { height: 100%; width: 100%; 
-webkit-transition: all 2s ease; -moz-transition: all 2s ease;
 -ms-transition: all 2s ease; transition: all 2s ease; } 
 .zoomin img:hover { 
 width:100%;height:100%;
  }  
- See more at: http://www.corelangs.com/css/box/zoom.html#sthash.rLChVgOk.dpuf
  */

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
/* .multipleslides { position:relative; height:332px; width:500px; float:left; }
.multipleslides > * { position:absolute; left:0; top:0; display:block; } */
 
 
</style>



<script type="text/javascript">
jQuery(document).ready(function() {
$('#status').select2();



});  
    </script>
<script lang="JavaScript">


function updateTextInput(val) {
	
	
    document.getElementById('points').value=val;
    //document.getElementById('status').value= status;
        $("#points").click(function () {
   //	alert("points status" + points);
       $("#slider").slider("option", "value", $("#points").val());

   });
   		
   		
 }
function msgCount()
{
	//alert("check message count");
	var e = document.getElementById("points").value;
	var a = "${merchantPromo.points}";
	
	if(e >= a)
	{
		alert("NonMerchant Count Dont exist:" + a);
		return false;
	}
	
	
	else
		{
		//alert("merchant Count:" + e);
		return false;
		}
	}

 function enableTextbox() {
	//alert("test status:");
	if (document.getElementById("status").value == "REPOST") {
	document.getElementById("points").disabled = false;
	
	
	document.getElementById("rangeInput").disabled = false;
	document.getElementById("datepicker").disabled = false;
	document.getElementById("datepicker1").disabled = false;
	
	}
	else {
	document.getElementById("points").disabled = true;
	document.getElementById("rangeInput").disabled = true;
	document.getElementById("datepicker").disabled = true;
	document.getElementById("datepicker1").disabled = true;
	}
	} 

function loadSelectData() {
	
	
	var e = document.getElementById("status").value;
    
    if (e == null || e == '' ) {
    
			alert("Please Select Status");
			 //form.submit = false; 
			return false;
			}
}


function loadtype(type,type1){
	
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

	
var status = document.getElementById("status1").value;
//alert('status val : '+status);
var status1 = document.getElementById("status"); 
loadtype(status,status1);

}
 
 
function loadCancelData() 
{
	//alert("fcancel data");
	
	
	 document.location.href = "${pageContext.request.contextPath}/promotionwebNonmerchant1/list/1";
	form.submit;
}

   

	</script>
<body onload="loadDropData();enableTextbox()">
	

 
	  
	 

					
					
					<form action="${pageContext.request.contextPath}<%=NonMerchantWebAddPromoController.URL_BASE%>/editMerchantPromotion"
		method="post" name="form1" id="form1"  commandName="merchantPromo" >

			<input type="hidden" name="${_csrf.parameterName}"
			value="${_csrf.token}" /> <input type="hidden" name="id"
			value="${merchantPromo.id}" />
<!-- <div style="overflow:auto;border:1px;width:100%"> -->
			<div class="content-wrapper"  >
       
        
        <div class="row">
			
            <div class="col-md-12 formContianer" >
              <h3 class="card-title">Edit NonMerchant EZYAds Details</h3>
            <!--   <a class="btn btn-primary btn-flat pull-right" href="#">Add New <i class="fa fa-lg fa-plus"></i></a> 
              <div class="col-md-12 formContianer tableEdit">style="width:90rem;"-->
            
          <div class="card" >
            	
              <!-- <h3 class="card-title userTitle">Edit<i class="fa fa-pencil pull-left" aria-hidden="true"></i> <span class="pull-right"> -->
                                 <h3 class="card-title userTitle"> NonMerchant Name:${merchantPromo.merchantName}			
						<input type="hidden" class="form-control" id="merchantName" name="merchantName" value="${merchantPromo.merchantName}">
					Mid:${merchantPromo.mid}
												
							<input type="hidden" class="form-control" id="mid" name="mid" value="${merchantPromo.mid}" >
						<!-- </span> --></h3>
            		
							<div class="row">
				
					
					
							<div class="form-group col-md-4">
								<div class="form-group">
									<label  for="lName">EZYAds Name</label>
									<%-- ${merchantPromo.promoName} --%>
								<%-- <input type="text" class="form-control" id="promoName" name="promoName" value="${merchantPromo.promoName}" readonly="readonly"> --%>
								
									<textarea  class="form-control" id="promoName"   style="word-break:break-all;" rows="2" cols="2" maxlength="120"
								placeholder="promoName" name="promoName" path="promoName" readonly="readonly">${merchantPromo.promoName}</textarea>
								</div>
							</div>
							
							
							
								
							<div class="form-group col-md-4">
								<div class="form-group">
									<label  for="fName">EZYAds Code</label>
									<%-- ${merchantPromo.pCode} --%>
								<input type="text" class="form-control" id="pCode" name="pCode" value="${merchantPromo.pCode}" readonly="readonly">
								</div>
					
					</div>
					
					<div class="form-group col-md-4">
								<div class="form-group">
									<label  for="lName">Mobile UserName</label>
									<%-- ${merchantPromo.deviceId} --%>
								<input type="text" class="form-control" id="username" name="username" value="${merchantPromo.username}" readonly="readonly">
								</div>
							</div></div>
					
					<div class="row">
					<div class="form-group col-md-4">
								<div class="form-group">
									<label  for="fName">EZYAds Desc</label>
								<input type="hidden" class="form-control" id="promoDesc" name="promoDesc" value="${merchantPromo.promoDesc}">
								
								<textarea  class="form-control" id="promoDesc"   style="word-break:break-all;" rows="2" cols="2" maxlength="120"
								placeholder="promoDesc" name="promoDesc" path="promoDesc" readonly="readonly">${merchantPromo.promoDesc}</textarea>
								</div>
					
					</div>
					
					
					<!-- <div class="zoomin frame"> -->
					<div class="form-group col-md-4">
								<div class="form-group">
									<label  for="fName">EZYAds Image</label>
									</div>
								<div class="form-group">	
								
								<input type="hidden" class="form-control" id="promoLogo1" placeholder="promoLogo1" name="promoLogo1" value="${merchantPromo.promoLogo1}" />
			<img width ="100"  height="100" src="data:image/jpg;base64,<c:out value='${merchantPromo.promoLogo1}'/>" />
			
				<c:choose>
							<c:when test="${merchantPromo.promoLogo2 != null}">
		
		<input type="hidden" class="form-control" id="promoLogo2" placeholder="promoLogo2" name="promoLogo2" value="${merchantPromo.promoLogo2}" />
			
			<img width ="100"  height="100"  src="data:image/jpg;base64,<c:out value='${merchantPromo.promoLogo2}'/>" />
			
		<!-- align="right" width ="100"  height="100"	 -->
			
</c:when>
</c:choose>
					</div>			
					</div><!-- </div> -->
							<div class="form-group col-md-4">
								<div class="form-group">
									<label  for="fName">Status</label>
							
								
								<c:choose>
							<c:when test="${merchantPromo.status =='REPOST'}">
										<input type="hidden" id="status1" value="${merchantPromo.status}" />
							<select class="form-control" id="status"  name="status"  path="status"  >
							
									<option value="">Status</option>
									<option value="REPOST">Repost</option>
									<option value="CANCELLED">Cancelled</option>
									
									
									
									</select>
									</c:when>
									
								<c:when test="${merchantPromo.status =='PENDING'}">
								<input type="hidden" id="status1" value="${merchantPromo.status}"  />
							<select class="form-control" id="status"  name="status"  path="status"  >
							
									<option value="">Status</option>
									<option value="PENDING">Pending</option>
									<option value="CANCELLED">Cancelled</option>
									
									
									
									</select>
									</c:when>
									
									
									<c:when test="${merchantPromo.status =='SENT'}">
								<input type="hidden" id="status1" value="${merchantPromo.status}" />
							<select class="form-control" id="status"  name="status"  path="status" onchange="enableTextbox()" >
									<option value="">Status</option>
									<option value="SENT" >Sent</option>
									<option value="REPOST" >Repost</option>
									<option value="DELETED">Deleted</option>
									</select>
									</c:when>
									<c:when test="${merchantPromo.status =='APPROVED'}">
								<input type="hidden" id="status1" value="${merchantPromo.status}"  />
							<select class="form-control" id="status"  name="status"  path="status"  >
							
									<option value="">Status</option>
									<option value="APPROVED">Approved</option>
									<option value="SENT">Sent</option>
									<option value ="DELETED">Deleted</option>
	                       	</select>
									</c:when>
									
									
									</c:choose>
								</div>
					
					</div>
					</div>
					<div class="row">
					
					<div class="form-group col-md-4">
								<div class="form-group">
									<label  for="fName">Message Count</label>
									<input type="range" name="rangeInput"  id= "rangeInput" min="1" max="${merchantPromo.points}"  value="${merchantPromo.points}" onchange="updateTextInput(this.value);">
								<input type="text" class="form-control" id="points" name="points" value="${merchantPromo.points}"  onchange= "return msgCount();updateTextInput(this.value)" >
								</div>
					
					</div>

					<div class="form-group col-md-4">
								<div class="form-group">
									<label  for="fName">Valid_From</label>
								
								
								<input type="hidden"
							name="validFrom1" id="validFrom1" value="${validDate1}"> <input
							type="text" class="form-control" id="datepicker" name="validFrom" value="${merchantPromo.validFrom}"
							 placeholder="dd/mm/yyyy"  >

					</div>
								
								</div>
								
								
								<div class="form-group col-md-4">
								<div class="form-group">
									<label  for="fName">Valid_To</label>
								
								
								<input type="hidden"
							name="validTo1" id="validTo1" value="${validDate2}"> <input
							type="text" class="form-control" id="datepicker1" name="validTo" value="${merchantPromo.validTo}"
							 placeholder="dd/mm/yyyy" >

					</div>
								
								</div>
					
					</div>
		
					
					</div>
					
				
           </div>  
          
           
             	<button class="btn btn-primary icon-btn" type="submit"
onclick=" return loadSelectData()">Submit</button>
			
			
      <button type="button"  class="btn btn-default icon-btn" onclick="return loadCancelData();msgCount()">Cancel</button>
      </div>       
   	 </div>
       
 </form>

 

	
	</body>
	</html>


