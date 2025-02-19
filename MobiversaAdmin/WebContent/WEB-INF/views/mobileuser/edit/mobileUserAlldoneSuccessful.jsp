<%@page import="com.mobiversa.payment.controller.MobileUserController"%>
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
<form action="${pageContext.request.contextPath}/mobileUser/mobileUserView/Details" method="post"> 
<input type="hidden" name="id" value="${mobileUser.tid}" />
<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}">
<div class="container-fluid">    
 <div class="row">
  <div class="col s12">
      <div class="card border-radius">
        <div class="card-content padding-card">
          <div class="d-flex align-items-center">
              <h3>Your request to edit mobile user for the following is successful</h3>
              </div>           
              <div class="row">
				<div class="input-field col s12 m6 l6 ">
				<label for="firstName">Mobile UserName</label>
				<input type="text"  name="mobileUserName"  readonly="readonly"
						value="${mobileUser.mobileUserName}" />
				</div>
				</div>
              
             

	<a href="${pageContext.request.contextPath}/<%=MobileUserController.URL_BASE%>/details" class="btn btn-primary">View Mobile User Details</a>
			</div></div></div></div></div>
				</form>
		
</body>		
