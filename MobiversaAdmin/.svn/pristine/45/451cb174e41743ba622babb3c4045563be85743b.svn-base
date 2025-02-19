<%@page import="com.mobiversa.payment.controller.MasterMerchantController"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>

<script type="text/javascript"  src="${pageContext.request.contextPath}/resourcesNew/js/urlHide.js"></script>
 <style>
	td, th { padding: 7px 8px;color:#707070; }
	thead th {  border-bottom: 1px solid #ffa500; color:#4377a2; }
	</style>
<body>

<div class="container-fluid">    



<form action="${pageContext.request.contextPath}/masterMerchant/detail/${masterMerchant.id}" method="post">

 <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />

 <div class="row">
  <div class="col s12">
      <div class="card blue-bg text-white">
        <div class="card-content" style="padding:0px;">
          <div class="d-flex align-items-center">
              <h3 >Your request to edit  Master Merchant for the following is successful</h3>
              </div>
              
               <div class="row">
   			<div class="input-field col s12 m3 l3">
   			<label for="Business Name">Master Merchant Name</label>
				 <input  type="text" name="firstName"  
					 id="firstName" value="${masterMerchant.firstName}" readonly="readonly">
   			</div>
   		<%-- 	<div class="input-field col s12 m3 l3">
   			<label for="Business Name">Agent Type</label>
					 <input type="text" name="code"  
					 id="code" value="${agent.agType}" readonly="readonly">
   			</div> --%>
   			</div>
            
		
		<a href="${pageContext.request.contextPath}<%=MasterMerchantController.URL_BASE%>/detail/${masterMerchant.id}" class="btn btn-primary">View Master Merchant Detail</a>
		
	</div></div>
	</div></div>
	</form>
	</div>
	
	</body>
	



