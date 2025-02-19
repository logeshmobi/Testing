


<%@page import="com.mobiversa.payment.controller.NonMerchantWebAddPromoController"%>
<%@page import="com.mobiversa.common.bo.Merchant"%>

<%@page import="com.mobiversa.common.bo.Promotion"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
 

<meta charset="UTF-8">
  <meta content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no" name="viewport">

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
window.history.replaceState({}, document.title, "/MobiversaAdmin/" + "promotionwebNonmerchant1/");
</script>

<script>
  $(function() {
    $( "#validityDate" ).datepicker();
  });
</script>

<script type="text/javascript">
function load1()
 {
	 var url = "${pageContext.request.contextPath}/promotionwebNonmerchant1/list"; 
		$(location).attr('href',url);
 }
</script>
<script type="text/javascript">
function load()
{
	
 //$('#testing').click(function(){
	 
	
      	swal({
      		title: "Are you sure? you want to add this NonMerchant Promotion Details",
      		text: "it will be added..!",
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
       			$("#form-add").submit();
      			
      			
      		} else {
      			// swal("Cancelled", "Your Merchant Promotion details not added", "error"); 
      			 var url = "${pageContext.request.contextPath}/promotionwebNonmerchant1/list"; 
      			$(location).attr('href',url);
      			//return true;
      		}
      	});
      //});
 
}
</script>

 
</head>
<body>

 
 

 <form:form action="merchantPromoDetailsReviewAndConfirm"  method="post" commandName="merchantPromo" id="form-add"  name="form-add">
<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
      
<div class="content-wrapper">
        
        
        <div class="row">
			
            <div class="col-md-12 formContianer">
              <h3 class="card-title">Create EZYAds</h3>
            <!--   <a class="btn btn-primary btn-flat pull-right" href="#">Add New <i class="fa fa-lg fa-plus"></i></a> -->
              <div class="col-md-12 formContianer tableEdit">
            <div class="card">
            	
             <!--  <h3 class="card-title userTitle">Add<i class="fa fa-pencil pull-left" aria-hidden="true"></i> <span class="pull-right"> -->
                                  <h3 class="card-title userTitle"> NonMerchant Name: ${merchantPromo.merchantName}				
						<input type="hidden" class="form-control" id="merchantName" name="merchantName" value="${merchantPromo.merchantName}" >
					Mid:	${merchantPromo.mid}
												
						<input type="hidden" class="form-control" id="mid" name="mid" value="${merchantPromo.mid}">
						<!-- </span> --> </h3>
           
											
						<!-- 	<div class="card-body formbox">	 -->
							 <div class="row">		
									<div class="form-group col-md-4">
								<div class="form-group">
									<label for="PromoName">EZYAds Name</label>
										<%-- <input type="text" class="form-control" id="promoName" name="promoName" value="${merchantPromo.promoName}" readonly="readonly"> --%>	
										
										<form:textarea  readonly="true" class="form-control" id="promoName"   style="word-break:break-all;" rows="2" cols="2" maxlength="120"
										placeholder="promoName" name="promoName" path="promoName"  value="${merchantPromo.promoName}" ></form:textarea>
									
									<%-- ${agent.addr1} --%>
									</div>
									</div>
									
									<div class="form-group col-md-4">
								<div class="form-group">
									<label for="SendType">Send To?</label>
										<input type="text" class="form-control" id="sendType" name="sendType" value="${merchantPromo.sendType}" readonly="readonly">	
									<%-- ${agent.addr2} --%>
									</div>
									</div>
									
									<div class="form-group col-md-4">
								<div class="form-group">
									<label for="city">EZYAds Desc</label>
										<%-- <input type="text" class="form-control" id="promoDesc" name="promoDesc" value="${merchantPromo.promoDesc}" readonly="readonly"> --%>	
										
										
										<form:textarea  readonly="true" class="form-control" id="promoDesc"   style="word-break:break-all;" rows="2" cols="2" maxlength="120"
										placeholder="promoDesc" name="promoDesc" path="promoDesc"  value="${merchantPromo.promoDesc}" ></form:textarea>
									
									</div>
									</div>
									</div><!-- </div> -->
							 <div class="row">		
									<div class="form-group col-md-4">
								<div class="form-group">
									<label for="PromoName">EZYAds Image</label>
									</div>	
										<div class="form-group">
										<img width ="100" height="100"  src="data:image/jpg;base64,<c:out value='${merchantPromo.promoLogo1}'/>" />
										<c:choose>
							<c:when test="${merchantPromo.promoLogo2 != null}">	
							<img width ="100"  height="100" src="data:image/jpg;base64,<c:out value='${merchantPromo.promoLogo2}'/>" />
							</c:when>
						</c:choose>
								
									</div>
									</div>
						<div class="form-group col-md-4">
								<div class="form-group">
									<label for="SendType">EZYAds Code</label>
										<input type="text" class="form-control" id="pCode" name="pCode" value="${merchantPromo.pCode}" readonly="readonly">	
								
									</div>
									</div>			
						
					<div class="form-group col-md-4">
								<div class="form-group">
									<label for="SendType">Message Count</label>
										<input type="text" class="form-control" id="points" name="points" value="${merchantPromo.points}" readonly="readonly">	
								
									</div>
									</div></div>
						
						 <div class="row">	
						<div class="form-group col-md-4">
								<div class="form-group">
									<label for="SendType">Mobile User Name</label>
										<input type="text" class="form-control" id="username" name="username" value="${merchantPromo.username}" readonly="readonly">	
								
									</div>
									</div>
					
					
					
					
					
						<div class="form-group col-md-4">
								<div class="form-group">
									<label for="validityDate">Valid_From</label>
										<input type="text" class="form-control" id="validFrom"  name="validFrom" value="${merchantPromo.validFrom}" readonly="readonly">	
								
								<%-- <fmt:formatDate value="$${dto.date}" /> --%>
									</div>
									</div>
									
				
				
				
				
				<div class="form-group col-md-4">
								<div class="form-group">
									<label for="validityDate">Valid_To</label>
										<input type="text" class="form-control" id="validTo"  name="validTo" value="${merchantPromo.validTo}" readonly="readonly">	
								
								<%-- <fmt:formatDate value="$${dto.date}" /> --%>
									</div>
									</div>
									</div>
						
</div>

<button type="button"  id="testing" class="btn btn-primary" onclick="return load()" >Confirm</button>  <!-- id="testing" -->
 
 <button type="button"  class="btn btn-primary" onclick="load1()" >Cancel</button> <!--  id="testing1" -->
 
 </div>
 
</div>


</div>
</div>
</form:form>

 

	
	</body>
	
	
	
	<%-- 
					
					
						<td>Promotion Image</td>
						<!-- </tr> -->
						
						
						<td valign="top" style="padding-top: 5px;"><img width ="100"  src="data:image/jpg;base64,<c:out value='${merchantPromo.promoLogo1}'/>" /></td>
						
							<c:choose>
							<c:when test="${merchantPromo.promoLogo2 != null}">
						
						<td valign="top" style="padding-top: 5px;"><img width ="100"  src="data:image/jpg;base64,<c:out value='${merchantPromo.promoLogo2}'/>" /></td>
						
						</c:when>
						</c:choose>
					</tr> --%>