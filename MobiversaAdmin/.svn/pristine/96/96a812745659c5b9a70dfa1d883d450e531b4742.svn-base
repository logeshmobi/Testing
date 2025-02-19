<%@page import="com.mobiversa.payment.controller.NonMerchantWebAddPromoController"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<!-- <script src="/resourcesNew/js/jQuery-2.1.4.min.js" type="text/javascript"></script>  -->

<html lang="en-US">
<head>
<meta charset="UTF-8">
  <meta content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no" name="viewport">
 <script type="text/javascript"  src="${pageContext.request.contextPath}/resourcesNew/js/urlHide.js"></script>
</head>
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

<body>

	  
	 
	
	   <input type="hidden" name="${_csrf.parameterName}"
			value="${_csrf.token}" /> <input type="hidden" name="id"
			value="${merchantPromo.id}" />
		<div style="overflow:auto;border:1px;width:100%">	
		<div class="content-wrapper">
  <div class="row">
			<div class="col-md-6">
           <div class="card" style="width: 50rem;">
              <h3 class="card-title">NonMerchant EZYAds Details</h3>
              <table class="table table-striped">
						
						<tbody>
						
						<tr>
						<td>NonMerchant Name</td>
						<td> ${merchantPromo.merchantName}</td>
			</tr>
			
			
			<tr >
			<td>Mid</td>
			<td> ${merchantPromo.mid}</td>
			</tr>
			
			<tr>
			<td style="width:30%;height:30%">EZYAds Name</td>
			<td> ${merchantPromo.promoName}</td>
			</tr>
			
			<tr>
			<td>EZYAds Code</td>
			<td>${merchantPromo.pCode}</td>
			</tr>
			
			<tr>
			<td>Mobile UserName</td>
			<td>${merchantPromo.username}</td>
			</tr>
			<tr>
			<td>EZYAds Desc</td>
			<td>${merchantPromo.promoDesc}</td>
			</tr>
			
			<%-- <tr  > <!--  height ="150" valign="top" style="padding-top: 5px;" -->
			
			<td >PromoImage</td>
			<td> 
		
 <img   align= "left" width="100" height="100" src="data:image/jpg;base64,<c:out value='${merchantPromo.promoLogo1}'/>" /></td>
 <c:choose>
							<c:when test="${merchantPromo.promoLogo2!= null}">
 <td > 
 <img   align ="right" width="100" height="100" src="data:image/jpg;base64,<c:out value='${merchantPromo.promoLogo2}'/>" /></td>
 
 </c:when>
 </c:choose> --%>
 
 
 
 <tr>
		
			<td >EZYAds Image</td>
			<td valign="top" style="padding-top: 5px;"> 
		
 <img   width="100" height="100"  src="data:image/jpg;base64,<c:out value='${merchantPromo.promoLogo1}'/>" />
 <c:choose>
							<c:when test="${merchantPromo.promoLogo2!= null}">
<!-- <td valign="top" style="padding-top: 5px;"> --> 
 <img  width="100" height="100"  src="data:image/jpg;base64,<c:out value='${merchantPromo.promoLogo2}'/>" />
 
 </c:when>
 </c:choose>

  </td>
</tr>
			
			
			<tr>
			<td>Valid_From</td>
			<td>${merchantPromo.validFrom}</td>
			</tr>
			
			<tr>
			<td>Valid_To</td>
			<td>${merchantPromo.validTo}</td>
			</tr>
			<tr>
			<td>Status</td>
			
			
			<td>${merchantPromo.status}</td>
			
	
						
						</tr>
								
		
			<tr>
			<td>Message Count</td>
			<td>${merchantPromo.points}</td>
			</tr>
			
			
			
						
						</tbody>
							
					</table>
					</div>
					
					
				
		
			<%-- <a href="${pageContext.request.contextPath}/<%=NonMerchantWebAddPromoController.URL_BASE%>/edit/${merchantPromo.id}"
			 class="btn btn-primary">Edit Details</a> --%>
			 
			 <form  method="post" id="form-edit" 
					action="${pageContext.request.contextPath}<%=NonMerchantWebAddPromoController.URL_BASE%>/editNonMerchantEzyAds" > 
	 
	       <input type="hidden" name="id" value="${merchantPromo.id}" />
	        <%-- <input type="text" name="id" value="${merchantPromo.id}" /> --%>
			<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}">
			
				<center><button type="submit" class="btn btn-primary" value="Edit Details">Edit Details</button></center>
				</form>
		</div> 		
             </div>
           </div>  
        </div>
        </body>
        </html>
        
		
