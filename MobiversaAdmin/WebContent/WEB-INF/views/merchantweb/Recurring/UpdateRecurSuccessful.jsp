<%@page	import="com.mobiversa.payment.controller.MerchantWebReaderController"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<html lang="en-US">
<head>
<meta charset="UTF-8">
<meta content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no" name="viewport">
<script type="text/javascript" src="${pageContext.request.contextPath}/resourcesNew/js/plugins/select2.min.js"></script>
<script type="text/javascript"  src="${pageContext.request.contextPath}/resourcesNew/js/jquery-2.1.4.min.js"></script>
<script src="${pageContext.request.contextPath}/resourcesNew/js/urlHide.js"></script>
</head>
<body>
<div class="container-fluid">   
<div class="row">
  <div class="col s12">
      <div class="card border-radius">
        <div class="card-content padding-card">
        
        
        <div class="row">
			<div class="col-md-6">
            <div class="card">
              <h3 class="card-title">Your request to edit Recurring for the following is successful</h3>
              <table class="table table-striped">
						
		
										
							<tbody>
							
							<tr>
							<td>Mid</td>
							<td>${ezyRec.mid}</td>
							</tr>
							<tr>
							<td>Status</td>
							<td>${ezyRec.status}</td>
							</tr>
							<tr>
							<td>Remarks</td>
							<td>${ezyRec.remarks}</td>
							</tr>
							
							
		</tbody>
		</table>
		</div>
		<button type="button" class="btn btn-primary">
		<a href="${pageContext.request.contextPath}/transactionMoto/recurringList" style="color:white;">Done</a></button>
		</div>
		
		

</div>

</div>

</div></div></div></div>
</body>
</html>

