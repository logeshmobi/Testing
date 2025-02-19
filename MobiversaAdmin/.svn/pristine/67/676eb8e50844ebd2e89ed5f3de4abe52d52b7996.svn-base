<%@page import="com.mobiversa.payment.controller.MerchantPictureController"%>
<%@page import="com.mobiversa.common.bo.Merchant"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>



<form action="${pageContext.request.contextPath}promotionweb1/details" method="post">  
  <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />

  <input type="hidden" name="id" value="${merchant.id}" />

<div class="content-wrapper">
        
        
        <div class="row">
			<div class="col-md-6">
            <div class="card">
              <h3 class="card-title">Your request to upload Merchant Profile for the following is successful</h3>
              <table class="table table-striped">
              
              
           <%--    
              <tbody>
						
						<tr>
						<td>Merchant Name</td>
						<td>${regAddMerchant.merchantName}</td>
						</tr>
						
						
						<tr>
						<td>Mid</td>
						<td>${regAddMerchant.mid}</td>
						</tr>
						
						
						 --%>
						
			</table>
			</div>
			</div>
			</div>
			</div>
			
			
			<a href="${pageContext.request.contextPath}<%=MerchantPictureController.URL_BASE%>/list/${merchant.id}" class="btn btn-primary">View Merchant Details</a>
						
					
						
			</form>
					