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

<div class="container-fluid">  
<div class="row">
			<div class="col s12">
      <div class="card blue-bg text-white">
        <div class="card-content">
          <div class="d-flex align-items-center">
              <h3 >Your request to Add  GrabPay User for the following is successful</h3>
              </div>
              <table class="table table-striped">
					<tbody>
						<tr>
							<td>Mobile UserName</td><td>${mobileuser.contactName}</td>
						</tr>
						
						</tbody>
				</table>
			

		<a href="${pageContext.request.contextPath}/<%=MobileUserController.URL_BASE%>/details/${mobileuser.id}"
			class="btn btn-primary icon-btn">View Mobile User Details</a>
			</div>
</div>		
	</div></div></div>
</form>



