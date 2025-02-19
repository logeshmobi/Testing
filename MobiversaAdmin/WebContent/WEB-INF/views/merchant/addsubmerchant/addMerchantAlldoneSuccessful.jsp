<%@page import="com.mobiversa.payment.controller.MerchantWebController"%>
<%@page import="com.mobiversa.common.bo.Merchant"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<script type="text/javascript">
window.history.replaceState({}, document.title, "/MobiversaAdmin/" + "admin/");

</script>
  <div class="container-fluid"> 
<%-- <form action="${pageContext.request.contextPath}admmerchant/details" method="post">  
  <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />

  <input type="hidden" name="id" value="${merchant.id}" /> --%>

<div class="row">
  <div class="col s12">
      <div class="card border-radius">
        <div class="card-content padding-card">
          <div class="d-flex align-items-center">
              <h3>Your request to Add Merchant for the following is successful</h3>
              </div>
              
              <div class="row">
   			<div class="input-field col s12 m3 l3">
   			<label for="Business Name">Business Name</label>
				 <input  type="text" name="businessName"  
					 id="businessName" value="${smerchant.businessName}" readonly="readonly">
   			</div>
   			<div class="input-field col s12 m3 l3">
   			<label for="Business Name">MID</label>
					 <input type="text" name="mid"  
					 id="mid" value="${smerchant.mid.subMerchantMID}" readonly="readonly">
   			</div>
   			</div>
         
						
						<%-- <a href="${pageContext.request.contextPath}<%=MerchantWebController.URL_BASE%>/list/${merchant.id}" class="btn btn-primary">View Merchant Details</a> --%>
						
						
						
						</div>
						</div></div>
						</div>
						
<!-- 							
</form> -->
</div>
</body>
</html>
	
	
			
	


	






