
<%@page import="com.mobiversa.payment.controller.MerchantWebController"%>

<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>
<!-- <script src="/resourcesNew/js/jQuery-2.1.4.min.js" type="text/javascript"></script>  -->
<!-- <form action="detail/merchant" method="post"> -->
<script src="${pageContext.request.contextPath}/resourcesNew/assets/js/jquery-1.11.3.min.js"></script>
<script type="text/javascript"  src="${pageContext.request.contextPath}/resourcesNew/js/urlHide.js"></script>
 <link href="${pageContext.request.contextPath}/resourcesNew1/assets/libs/chartist/dist/chartist.min.css" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/resourcesNew1/assets/libs/chartist-plugin-tooltips/dist/chartist-plugin-tooltip.css" rel="stylesheet">
     
    <link href="${pageContext.request.contextPath}/resourcesNew1/assets/extra-libs/c3/c3.min.css" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/resourcesNew1/dist/css/style.css" rel="stylesheet">
     
    <link href="${pageContext.request.contextPath}/resourcesNew1/dist/css/pages/dashboard1.css" rel="stylesheet">
	
	  <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resourcesNew1/assets/libs/pickadate/lib/themes/default.css">
        <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resourcesNew1/assets/libs/pickadate/lib/themes/default.date.css">
        <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resourcesNew1/assets/libs/pickadate/lib/themes/default.time.css">
 
 
 
 <div class="container-fluid">    
  <div class="row">
  <div class="col s12">
      <div class="card border-radius">
        <div class="card-content padding-card">
<form action="${pageContext.request.contextPath}merchant/detail?manualSettlement=${manualSettlement}" method="post"> 

<div class="content-wrapper">
        
        
        <div class="row">
			<div class="col-md-6">
            <div class="card">
              <h3 class="card-title">Your request to Edit Merchant for the following is successful</h3>
              <table class="table table-striped">
						<tbody>
						
						<c:if test="${merchant.mid != null}">
						<tr>
						<td>Mid</td>
						<td>${merchant.mid}</td>
						</tr>
						</c:if>
						
						<%-- <c:if test="${merchant.umMid != null}">
						<tr>
						<td>UM_Mid</td>
						<td>${merchant.umMid}</td>
						</tr>
						</c:if> --%>
						
						<tr>
						<td>Business Name</td>
						<td>${merchant.businessName}</td>
						</tr>
						</tbody>
						
						</table>
						</div>
						
						<a href="${pageContext.request.contextPath}<%=MerchantWebController.URL_BASE%>/detail/${merchant.id}?manualSettlement=${manualSettlement}" class="btn btn-primary">View Merchant Details</a>  
						
						</div>
					
						</div></div>
						
								
</form>
</div></div></div>
</div></div>

</body>
</html>
	
	