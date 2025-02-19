<%@page import="com.mobiversa.payment.controller.MobileUserController"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>

 
<form action="${pageContext.request.contextPath}/mobileUser/details" method="post">
	<input type="hidden" name="${_csrf.parameterName}"
		value="${_csrf.token}" />
<div class="container-fluid"> 
<div class="row">
  <div class="col s12">
      <div class="card border-radius">
        <div class="card-content padding-card">
        <div class="d-flex align-items-center">
              <h3 >Your request to update GrabPay for the following user is successful</h3>
			</div>
			<table class="table table-striped">
					<tbody>
						<tr>
							<td>Mobile User Name</td><td>${mobileuser.username}</td>
						</tr>
						
						</tbody>
				</table>

			</div>

	</div></div></div></div>
</form>



