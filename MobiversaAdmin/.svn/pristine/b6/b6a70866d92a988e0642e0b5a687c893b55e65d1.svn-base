<%@page
	import="com.mobiversa.payment.controller.NonMerchantWebPromotionController"%>
	<%@page
	import="com.mobiversa.common.bo.Merchant"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>
	<script type="text/javascript">
window.history.replaceState({}, document.title, "/MobiversaAdmin/" + "promotionwebNonmerchant/");
</script>
<form
	action="${pageContext.request.contextPath}/promotionwebNonmerchant/custMailUpld"
	method="post">

	<input type="hidden" name="${_csrf.parameterName}"
		value="${_csrf.token}" />

	<div class="content-wrapper">
        
        
        <div class="row">
			<div class="col-md-6">
            <div class="card">
              <h3 class="card-title">Your request to add EmailFile for the
					following is successful</h3>
              <table class="table table-striped">
					




					<tbody>
						<tr>
							<td>NonMerchant Name</td>
							<td>${merchant.merchantName}</td>

						</tr>
					</tbody>

				</table>
			</div>
		</div>


	</div>
		
		<a	href="${pageContext.request.contextPath}<%=NonMerchantWebPromotionController.URL_BASE%>/uploadCustMail"
			class="btn btn-primary">View NonMerchant Detail</a>


</div>

</form>

</body>
</html>
