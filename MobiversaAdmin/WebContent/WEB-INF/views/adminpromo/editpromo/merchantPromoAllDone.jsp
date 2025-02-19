
<%@page import="com.mobiversa.payment.controller.PromotionAdminController"%>

<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>
<!-- <script src="/resourcesNew/js/jQuery-2.1.4.min.js" type="text/javascript"></script>  -->
<!-- <form action="detail/merchant" method="post"> -->
 <script type="text/javascript"  src="${pageContext.request.contextPath}/resourcesNew/js/urlHide.js"></script>
 
<form action="${pageContext.request.contextPath}promotionAdmin/detail" method="post">

<div class="content-wrapper">
        
        
        <div class="row">
			<div class="col-md-6">
            <div class="card">
              <h3 class="card-title">Your request to Update Merchant EZYAds for the following is successful</h3>
              <table class="table table-striped">
<tbody>

<tr>
							<td>Merchant Name</td>
							<td>${mobiPromo.merchantName}</td>
						</tr>
						<tr>
							<td>MID</td>
							<td>${mobiPromo.mid}</td>
						</tr>


<tr>
							<td>EZYAds Name</td>
							<td>${mobiPromo.promoName}</td>
						</tr>
						
						
					</tbody>

				</table>
			</div>
		
		<a type="button"
			href="${pageContext.request.contextPath}<%=PromotionAdminController.URL_BASE%>/detail/${mobiPromo.id}"
			class="btn btn-primary">View Merchant Detail</a>
			</div>
</div>
	</div>
	<!-- </div> -->
</form>

