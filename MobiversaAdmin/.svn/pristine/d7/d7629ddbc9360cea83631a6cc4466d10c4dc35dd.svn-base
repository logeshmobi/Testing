<%@page import="com.mobiversa.payment.controller.MobileUserController"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>
 
  <div class="container-fluid">   
<form action="${pageContext.request.contextPath}/mobileUser/details" method="post">
	<input type="hidden" name="${_csrf.parameterName}"
		value="${_csrf.token}" />
   
<div class="row">
  <div class="col s12">
      <div class="card blue-bg text-white">
        <div class="card-content" style="padding:0px;">
          <div class="d-flex align-items-center">
   			<h3 >Your request to Add  MobileUser for the following is successful</h3>
   			</div>
   			
   			<div class="row">
   			<div class="input-field col s12 m3 l3">
   			<label for="Business Name">Mobile UserName</label>
				 <input  type="text" placeholder="businessName" name="businessName"  
					 id="businessName" value="${mobileuser.contactName}" readonly="readonly">
   			</div>
   			<div class="input-field col s12 m3 l3">
   			<label for="Business Name">MobileUser ActivationCode</label>
					 <input type="text" placeholder="mobileUserName" name="mobileUserName"  
					 id="mobileUserName" value="${mobileuser.activationCode}" readonly="readonly">
   			</div>
   			</div>
   			
   				
   				<a href="${pageContext.request.contextPath}/<%=MobileUserController.URL_BASE%>/details/${mobileuser.id}"
			class="btn btn-primary icon-btn">View Mobile User Details</a>
   				
   				
             
			
			</div>
						</div></div>
						</div>


</form>
</div>




