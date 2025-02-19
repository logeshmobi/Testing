<%@page import="com.mobiversa.payment.controller.AdminController"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>

<head>
<style>
	td, th { padding: 7px 8px;color:#707070; }
	thead th {  border-bottom: 1px solid #ffa500; color:#4377a2; }
	</style>
</head>
<body>
<input type="hidden" name="id" value="${salutation}" />
<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}">
<div class="container-fluid">    
 <div class="row">
  <div class="col s12">
      <div class="card border-radius">
        <div class="card-content padding-card">
          <div class="d-flex align-items-center">
              <h3>Your request to edit Sub Merchant Profile for the following is successful</h3>
              </div>           
              <div class="row">
				<div class="input-field col s12 m6 l6 ">
				<label for="firstName">Business Name</label>
				<input type="text"  name="businessName"  readonly="readonly"
						value="${merchant.businessName}" />
				</div>
				</div>
              
             

	<a href="${pageContext.request.contextPath}/<%=AdminController.URL_BASE%>/submerchantSum" class="btn btn-primary">View Sub Merchant Summary</a>
			</div></div></div></div></div>
				
		
</body>		
