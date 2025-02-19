<%@page	import="com.mobiversa.payment.controller.MerchantWebReaderController"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<html lang="en-US">
<head>
<meta charset="UTF-8">
<meta content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no" name="viewport">
<link href="https://fonts.googleapis.com/icon?family=Material+Icons" rel="stylesheet">
    <link href="http://maxcdn.bootstrapcdn.com/bootstrap/3.2.0/css/bootstrap.min.css" rel="stylesheet">
    <script src="http://ajax.googleapis.com/ajax/libs/jquery/1.7.1/jquery.min.js"></script>
<link rel="stylesheet" href="http://cdn.datatables.net/1.10.2/css/jquery.dataTables.min.css"></style>
<script type="text/javascript" src="http://cdn.datatables.net/1.10.2/js/jquery.dataTables.min.js"></script>
<script type="text/javascript" src="http://maxcdn.bootstrapcdn.com/bootstrap/3.2.0/js/bootstrap.min.js"></script> 
    <link href="${pageContext.request.contextPath}/resourcesNew1/assets/libs/chartist/dist/chartist.min.css" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/resourcesNew1/assets/libs/chartist-plugin-tooltips/dist/chartist-plugin-tooltip.css" rel="stylesheet">
     
    <link href="${pageContext.request.contextPath}/resourcesNew1/assets/extra-libs/c3/c3.min.css" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/resourcesNew1/dist/css/style.css" rel="stylesheet">
     
    <link href="${pageContext.request.contextPath}/resourcesNew1/dist/css/pages/dashboard1.css" rel="stylesheet">
</head>
<body>
<div class="container-fluid">   
<div class="row">
  <div class="col s12">
      <div class="card border-radius">
        <div class="card-content padding-card">
           
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
		<button type="button" class="btn btn-primary">
		<a href="${pageContext.request.contextPath}/admmerchant/recurringList" style="color:white;">Done</a></button>
		</div>
		
		</div>
		
		

</div>

</div>

</div>
</body>
</html>

