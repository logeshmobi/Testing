<%@page import="com.mobiversa.payment.controller.MobileUserController"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>


<%-- <form action="mobileUser/mobileUserViewDetails/${mobileuser.id}" method="post"> --%>

 <div class="container-fluid"> 
<form action="${pageContext.request.contextPath}/mobileUser/updateMobileUser" method="post">
	<input type="hidden" name="${_csrf.parameterName}"
		value="${_csrf.token}" />

<div class="row">
  <div class="col s12">
      <div class="card border-radius">
        <div class="card-content padding-card">
          <div class="d-flex align-items-center">
              <h3 class="card-title">Your request to Update DTL for the following is successful</h3>
              </div>
              
              <div class="row">
              <c:if test="${mobileUser.um_motoMid != null}">	
   			<div class="input-field col s12 m3 l3">
   			<label for="Business Name">Moto MID</label>
				 <input  type="text" name="businessName"  
					 id="businessName" value="${mobileuser.um_motoMid}" readonly="readonly">
   			</div>
   			<div class="input-field col s12 m3 l3">
   			<label for="Business Name">DTL</label>
					 <input type="text" name="DTL"  
					 id="DTL" value="${mobileuser.DTL}" readonly="readonly">
   			</div>
   			</c:if>
   			<c:if test="${mobileUser.um_ezywayMid != null}">	
   			<div class="input-field col s12 m3 l3">
   			<label for="Business Name">Ezyway MID</label>
				 <input  type="text" name="businessName1"  
					 id="businessName1" value="${mobileuser.um_ezywayMid}" readonly="readonly">
   			</div>
   			<div class="input-field col s12 m3 l3">
   			<label for="Business Name">DTL</label>
					 <input type="text" name="DTL1"  
					 id="DTL1" value="${mobileuser.DTL1}" readonly="readonly">
   			</div>
   			</c:if>
   			
   			</div>
              
              <a href="${pageContext.request.contextPath}/<%=MobileUserController.URL_BASE%>/updateMobileUser"
			class="btn btn-primary icon-btn">Back</a>
      
			</div>

		
			</div>
</div>		
	</div>
</form>
</div>



