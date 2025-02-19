<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@page import="com.mobiversa.payment.controller.PromotionAdminController"%>
<%@page import="com.mobiversa.common.bo.Merchant"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
 
  
<!-- <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"> -->
<html lang="en-US">
 <head>
 <meta charset="UTF-8">
  <meta content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no" name="viewport">
 <script type="text/javascript"  src="${pageContext.request.contextPath}/resourcesNew/js/urlHide.js"></script>
 
  <style>
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


function load1()
{
	 var url = "${pageContext.request.contextPath}/promotionAdmin/list/1";
	  $(location).attr('href',url);
}

function load()
{
	
/*  $('#testing').click(function(){ */
	 
	
      	swal({
      		title: "Are you sure? you want to update this Merchant EZYAds Details",
      		text: "it will be updated..!",
      		type: "warning",
      		showCancelButton: true,
      		confirmButtonText: "Yes, add it!",
      		cancelButtonText: "No, cancel!",
      		closeOnConfirm: false,
      		closeOnCancel: false,
      	   confirmButtonClass: 'btn btn-success',
      	  cancelButtonClass: 'btn btn-danger',
      		
      	}, function(isConfirm) {
      		if (isConfirm) {
      			
       			//swal("Added!", "Your Merchant Promotion details added","success");
      			$("#form-edit").submit();
      			
      			
      		} else {
      			 //swal("Cancelled", "Your Merchant Promotion details not added", "error"); 
      			 var url = "${pageContext.request.contextPath}/promotionAdmin/list"; 
  $(location).attr('href',url);
      			//return true;
      		}
      	});
     // });
 
}
  

  
  
  </script>
</head>
<body  onload="addrow()">

					
	<form  method="post" id="form-edit" 
			action="${pageContext.request.contextPath}<%=PromotionAdminController.URL_BASE%>/editReviewandConfirm" 
			commandName="merchantPromo"> 
	 
	 <input type="hidden" name="id"
			value="${merchantPromo.id}" /><input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}">
			
		
		
		<div class="content-wrapper">
        
        
        <div class="row">
			
            <div class="col-md-12 formContianer">
              <h3 class="card-title">Edit EZYAds</h3>
            <!--   <a class="btn btn-primary btn-flat pull-right" href="#">Add New <i class="fa fa-lg fa-plus"></i></a> -->
              <!-- <div class="col-md-12 formContianer tableEdit"> -->
            <div class="card">
            	
              <!-- <h3 class="card-title userTitle">Edit<i class="fa fa-pencil pull-left" aria-hidden="true"></i> <span class="pull-right"> -->
                                 <h3 class="card-title userTitle"> Merchant Name: ${merchantPromo.merchantName}				
						<input type="hidden" class="form-control" id="merchantName" name="merchantName" value="${merchantPromo.merchantName}" >
					MID: ${merchantPromo.mid}
												
						<input type="hidden" class="form-control" id="mid" name="mid" value="${merchantPromo.mid}">
					<!-- 	</span> --> </h3>
							
	 <div class="row">		
									<div class="form-group col-md-4">
								<div class="form-group">
									<label for="PromoName">EZYAds Name</label>
										<input type="text" class="form-control" id="promoName" name="promoName" value="${merchantPromo.promoName}" readonly="readonly">	
									<%-- ${agent.addr1} --%>
									</div>
									</div>
									
									<div class="form-group col-md-4">
								<div class="form-group">
									<label for="pCode">EZYAds Code</label>
									<input type="text" class="form-control" id="pCode" name="pCode" value="${merchantPromo.pCode}" readonly="readonly">
									<%-- ${agent.addr2} --%>
									</div>
									</div>
									
									<div class="form-group col-md-4">
								<div class="form-group">
									<label for="city">EZYAds Desc</label>
										<input type="hidden" class="form-control" id="promoDesc" name="promoDesc" value="${merchantPromo.promoDesc}" readonly="readonly">	
										
										
										<textarea   class="form-control" id="promoDesc"   style="word-break:break-all;"  readonly="true" row="2"  column="2" max-length="120"
										placeholder="promoDesc" name="promoDesc" path="promoDesc"   >${merchantPromo.promoDesc}</textarea>
									
									</div>
									</div>
									</div>
							 <div class="row">		
									<div class="form-group col-md-4">
								<div class="form-group">
									<label for="PromoName">EZYAds Image</label>
									</div>	
										<div class="form-group">
										<img width ="100" height="100"   src="data:image/jpg;base64,<c:out value='${merchantPromo.promoLogo1}'/>" />
										<c:choose>
							<c:when test="${merchantPromo.promoLogo2 != null}">	
							<img width ="100" height="100" src="data:image/jpg;base64,<c:out value='${merchantPromo.promoLogo2}'/>" />
							</c:when>
						</c:choose>
								
									</div>
									</div>
									
									
									<div class="form-group col-md-4">
								<div class="form-group">
									<label for="pCode">Mobile User Name</label>
									<input type="text" class="form-control" id="username" name="username" value="${merchantPromo.username}" readonly="readonly">
									<%-- ${agent.addr2} --%>
									</div>
									</div>
								
								<div class="form-group col-md-4">
								<div class="form-group">
									<label for="PromoCode">Message Count</label>
										<input type="text" class="form-control" id="points" name="points" value="${merchantPromo.points}" readonly="readonly">	
								
									</div>
									</div>	
									
									
									<div class="form-group col-md-4">
								<div class="form-group">
									<label for="PromoCode">Valid_From</label>
										<input type="text" class="form-control" id="validFrom" name="validFrom" value="${merchantPromo.validFrom}" readonly="readonly">	
								
									</div>
									</div>	
									
									
									<div class="form-group col-md-4">
								<div class="form-group">
									<label for="PromoCode">Valid_To</label>
										<input type="text" class="form-control" id="validTo" name="validTo" value="${merchantPromo.validTo}" readonly="readonly">	
								
									</div>
									</div>	
									
									
							<div class="form-group col-md-4">
								<div class="form-group">
									<label for="PromoCode">Status</label>
										<input type="text" class="form-control" id="status" name="status" value="${merchantPromo.status}" readonly="readonly">	
								
									</div>
									</div>			
					

</div></div>

<button type="button"  class="btn btn-primary icon-btn" id="testing" onclick="return load()" >Confirm</button>  <!-- id="testing" -->
 
 <button type="button"  class="btn btn-default icon-btn" onclick="load1()" >Cancel</button> <!--  id="testing1" -->
 
</div></div></div>
</div>
</form>


	
	</body>
	</html>
							
