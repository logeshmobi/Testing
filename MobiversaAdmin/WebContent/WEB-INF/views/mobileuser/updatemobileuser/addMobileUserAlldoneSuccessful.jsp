<%@page import="com.mobiversa.payment.controller.MobileUserController"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>


<%-- <form action="mobileUser/mobileUserViewDetails/${mobileuser.id}" method="post"> --%>

<form action="${pageContext.request.contextPath}/mobileUser/details" method="post">
	<input type="hidden" name="${_csrf.parameterName}"
		value="${_csrf.token}" />

<div class="content-wrapper">
        
        
        <div class="row">
			<div class="col-md-6">
            <div class="card">
              <h3 class="card-title">Your request to Update  MobileUser for the following is successful</h3>
              <table class="table table-striped">
					<tbody>
						<tr><td>Mobile UserName</td><td>${mobileuser.contactName}</td></tr>
						<tr><td>MobileUser ActivationCode</td><td>${mobileuser.activationCode}</td></tr>
					</tbody>
				</table>
			</div>

		<a href="${pageContext.request.contextPath}/<%=MobileUserController.URL_BASE%>/details/${mobileuser1.id}"
			class="btn btn-primary icon-btn">View Mobile User Details</a>
			</div>
</div>		
	</div>
</form>



