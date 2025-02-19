<%@page import="com.mobiversa.payment.controller.MobileUserController"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<meta charset="UTF-8">
<meta
	content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no"
	name="viewport">

   

</head>
<body>
<%-- <form action="${pageContext.request.contextPath}/<%=MobileUserController.URL_BASE%>/editMobileUserReviewandConfirm" method="post" id="form-edit" commandName="mobileUser"> --%>
<div class="container-fluid">    
  <div class="row">
  
 
  
  <div class="col s12">
      <div class="card blue-bg text-white">
        <div class="card-content">
          <div class="d-flex align-items-center">
           <h3 class="text-white">  <strong>MobileUser updated with Device Id </strong></h3>
          </div>
          
          
        </div>
      </div>
    </div>
    </div>
    <div class="row">
  <div class="col s12">
      <div class="card border-radius">
        <div class="card-content padding-card">
 <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />

 
              <div class="row">
				<div class="input-field col s12 m6 l6 ">
						<label for="mailId">Mobile UserName</label>
					 <input type="text" class="form-control"
							id="txtMid" name="code" value="${mobileUser.username}" readonly>
					</div>
				<div class="input-field col s12 m6 l6 ">
						<label for="mailId">Status</label>
						 <input type="text" class="form-control"
							id="txtMid" name="firstName" value="${mobileUser.status}" readonly>
					</div>
				</div>
				 <div class="row">
				<div class="input-field col s12 m6 l6 ">
						<label for="tid">EZYWIRE TID</label>
						 <input type="text" class="form-control"
							id="txtMid" name="tid" value="${mobileUser.tid}" readonly>
					</div>
				<div class="input-field col s12 m6 l6 ">
						<label for="motoTid">EZYMOTO TID</label>
						 <input type="text" class="form-control"
							id="motoTid" name="motoTid" value="${mobileUser.motoTid}" readonly>
					</div>
				<div class="input-field col s12 m6 l6 ">
						<label for="ezyrecTid">EZYREC TID</label>
						 <input type="text" class="form-control"
							id="ezyrecTid" name="ezyrecTid" value="${mobileUser.ezyrecTid}" readonly>
					</div>
				<div class="input-field col s12 m6 l6 ">
						<label for="ezypassTid">EZYPASS TID</label>
						 <input type="text" class="form-control"
							id="ezypassTid" name="ezypassTid" value="${mobileUser.ezypassTid}" readonly>
					</div>
				<div class="input-field col s12 m6 l6 ">
						<label for="mailId">Pre-Auth</label>
					 <input type="text" class="form-control"
							id="email" name="email" value="${mobileUser.preAuth}" readonly>
					</div>
				</div>
				 <div class="row">
				<div class="input-field col s12 m6 l6 ">
						<label for="mailId">Device ID</label>
					 <input type="text" class="form-control"
							id="preAuth" name="preAuth" value="${mobileUser.deviceId}" readonly>
					</div>
				</div>
		
				
	
	<a href="${pageContext.request.contextPath}/mobileUser/devicemap" class="btn btn-primary blue-btn">View Device Mapping</a>
	<style>
							
				.select-wrapper .caret { fill: #005baa;}
							
				 .blue-btn { background-color:#005baa; color:#fff;border-radius:20px;}
				.button-class { float:right;}
				.float-right {float:right; }
				</style>

</div>
 </div>
</div>
</div>
</div>


	
	</body>
	</html>


