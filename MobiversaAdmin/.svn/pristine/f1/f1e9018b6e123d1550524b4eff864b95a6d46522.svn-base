<%@page import="com.mobiversa.payment.controller.NonMerchantWebAddPromoController"%>
<%@page import="com.mobiversa.common.bo.Merchant"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>

<script type="text/javascript">
window.history.replaceState({}, document.title, "/MobiversaAdmin/" + "promotionwebNonmerchant1/");
</script>

<form action="${pageContext.request.contextPath}promotionwebNonmerchant1/details" method="post">  
  <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />

  <input type="hidden" name="id" value="${merchant.id}" />

<div class="content-wrapper">
        
        
        <div class="row">
			<div class="col-md-6">
            <div class="card">
              <h3 class="card-title">Your request to Create NonMerchant EZYAds for the following is successful</h3>
              <table class="table table-striped">
					
						<tbody>
						
						<tr>
						<td>NonMerchant Name</td>
						<td>${merchantPromo.merchantName}</td>
						</tr>
						
						
						<tr>
						<td>Mid</td>
						<td>${merchantPromo.mid}</td>
						</tr>
						
						
						<tr>
						<td>EZYAds Name</td>
						<td>${merchantPromo.promoName}</td>
						</tr>
						
						
						<tr>
						<td>EZYAds Code</td>
						<td>${merchantPromo.pCode}</td>
						</tr>
						</tbody>
						
						</table>
						</div>
						</div>
						</div>
						<a href="${pageContext.request.contextPath}<%=NonMerchantWebAddPromoController.URL_BASE%>/list/${merchant.id}" class="btn btn-primary">View NonMerchant Details</a>
						
						</div>
						
								
</form>
</body>
</html>
	
	
			
	


	