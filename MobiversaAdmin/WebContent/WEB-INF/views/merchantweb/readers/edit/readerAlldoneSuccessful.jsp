<%@page	import="com.mobiversa.payment.controller.MerchantWebReaderController"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>
<div class="container-fluid">  
<div class="row">
<div class="col s12">
      <div class="card border-radius">
        <div class="card-content padding-card">
        <div class="d-flex align-items-center">
              <h3>Your request to edit reader for the following is successful</h3>
              </div>
              
              <div class="row">
	<div class="input-field col s12 m6 l6  ">
	
	<label  for="deviceId">Device ID</label>
	<input type="text"   id="deviceId" name="deviceId" value="${reader.deviceId}" >
              </div></div>
	
		<form  method="post" id="form-edit" 
					action="${pageContext.request.contextPath}<%=MerchantWebReaderController.URL_BASE%>/detail" > 
	
	       <input type="hidden" name="deviceId" value="${reader.deviceId}" />
	      
			<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}">
			
				<button type="submit" class="btn btn-primary" value="View Reader Detail">View Reader</button>
				</form>

</div>
</div></div></div></div>

</body>
</html>

