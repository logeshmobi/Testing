<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<html lang="en-US">
<head>
<meta charset="UTF-8">
<meta
	content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no"
	name="viewport">
	<link href="${pageContext.request.contextPath}/resourcesNew1/assets/libs/chartist/dist/chartist.min.css" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/resourcesNew1/assets/libs/chartist-plugin-tooltips/dist/chartist-plugin-tooltip.css" rel="stylesheet">
     
    <link href="${pageContext.request.contextPath}/resourcesNew1/assets/extra-libs/c3/c3.min.css" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/resourcesNew1/dist/css/style.css" rel="stylesheet">
     
    <link href="${pageContext.request.contextPath}/resourcesNew1/dist/css/pages/dashboard1.css" rel="stylesheet">
	
	  <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resourcesNew1/assets/libs/pickadate/lib/themes/default.css">
        <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resourcesNew1/assets/libs/pickadate/lib/themes/default.date.css">
        <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resourcesNew1/assets/libs/pickadate/lib/themes/default.time.css">
<script type="text/javascript"
	src="${pageContext.request.contextPath}/resourcesNew/js/urlHide.js"></script>
	
<body >
<div class="container-fluid">    
  <div class="row">
	 <div class="card border-radius">
        <div class="card-content padding-card">
<form:form method="get" id="form1" name="form1" commandName="txnDet"
		action="${pageContext.request.contextPath}/transaction/umEzywayList/1" >

	
		<div style="overflow: auto; border: 1px; width: 100%">
			<div class="content-wrapper">

<p><h3 style="color: blue;font-weight: bold;">${responseData}</h3></p>
				<div class="row">

					<div class="col-md-6">
						<!-- <div class="card"> -->


						<div class="card" style="width: 50rem;">
						
						
						
							<h3 class="card-title">EZYWAY Payment has been Cancelled</h3>
							<table class="table table-striped" width="100%">
								<tbody>
								
									<tr>
										<td><label class="control-label">Transaction Date/Time:</label></td>
										<td>${TDT}</td>
										
									</tr>

									
									<tr>
										<td><label class="control-label">Amount</label></td>
										<td>${amount}</td>
										
										
									</tr>							
									<%-- <tr>
										<td><label class="control-label">Card No:</label></td>
										<td>${CHNumber}</td>
										 
									</tr> --%>


								</tbody>
							</table>
							
							
				
						</div>
<div class="form-group col-md-4 i">
										<div class="form-group">

											<button type="submit" class="btn btn-primary">Done</button>
										</div>
									</div>
					</div>
				</div>

			
				
			</div>
		</div>
	
	
	
	
	</form:form>
	</div></div></div></div>
	</body>
	</html>
	
	