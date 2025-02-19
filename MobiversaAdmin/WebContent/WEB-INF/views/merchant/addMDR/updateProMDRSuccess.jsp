<%@page import="com.mobiversa.payment.controller.MobileUserController"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>


<div class="container-fluid">  
<div class="row">
  <div class="col s12">

<form action="${pageContext.request.contextPath}/mobileUser/details" method="post">
	<input type="hidden" name="${_csrf.parameterName}"
		value="${_csrf.token}" />

			 <div class="d-flex align-items-center">
              <h3>Your request to update Product MDR for the following MID is successful</h3>
              </div>
              <table class="table table-striped">
					<tbody>
						<tr>
							<td>MID</td><td>${MDR.mid}</td>
						</tr>
						<tr>
							<td>Product</td><td>${MDR.prodType}</td>
						</tr>
						
						</tbody>
				</table> 
				</form>
			</div>
			</div>
</div>		





