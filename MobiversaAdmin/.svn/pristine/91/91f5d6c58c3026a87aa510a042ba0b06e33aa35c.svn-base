
<%@page import="com.mobiversa.payment.controller.NonMerchantWebAddPromoController"%>

<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>
<script type="text/javascript"  src="${pageContext.request.contextPath}/resourcesNew/js/urlHide.js"></script>
<%-- <form action="${pageContext.request.contextPath}promotionwebNonmerchant1/detail" method="post">
 --%>
<div class="content-wrapper">
        
        
        <div class="row">
			<div class="col-md-6">
            <div class="card">
              <h3 class="card-title">Your request to edit NonMerchant EZYAds for the following is successful</h3>
              <table class="table table-striped">
					<tbody>



<tr>
							<td style="width:30%;height:30%">NonMerchant Name</td>
							<td>${promo.merchantName}</td>
						</tr>
						<tr>
							<td>Mid</td>
							<td>${promo.mid}</td>
						</tr>



<tr>
							<td>EZYAds Name</td>
							<td>${promo.promoName}</td>
						</tr>
						
					</tbody>

				</table>
			</div>
		
		<%-- <a type="button"
			href="${pageContext.request.contextPath}<%=NonMerchantWebAddPromoController.URL_BASE%>/detail/${promo.id}"
			class="btn btn-primary">View NonMerchant Detail</a> --%>
			
			<form  method="post" id="form-edit" 
					action="${pageContext.request.contextPath}<%=NonMerchantWebAddPromoController.URL_BASE%>/detail" > 
	 
	      <input type="hidden" name="status" value="${promo.status}" /> 
	       <input type="hidden" name="id" value="${promo.id}" />
	        <%-- <input type="text" name="staus" value="${dto.status}" /> --%>
			<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}">
				<center><button type="submit" class="btn btn-primary">View Merchant Detail</button></center>
				</form>
			</div>
</div>
	</div>
	
<!-- </form> -->

